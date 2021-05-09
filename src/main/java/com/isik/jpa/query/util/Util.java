package com.isik.jpa.query.util;

import com.google.common.primitives.Primitives;
import com.isik.jpa.query.api.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Utility methods for query builder.
 *
 * @author fatih
 */
public class Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    private Util() {
    }

    /**
     * finds filter field and applies predicate
     * @param biFunction
     * @param root
     * @param rootClazz
     * @param columnClazz
     * @param filter
     * @param cb
     * @param <Y>
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     */
    public static <Y, T> Predicate getPredicate(BiFunction<Expression<Y>, Y, Predicate> biFunction,
                                                Root<T> root, Class<T> rootClazz, Class<Y> columnClazz, Filter filter,
                                                CriteriaBuilder cb) throws NoSuchFieldException {
        Expression<Y> filterPath = Util.getFilterPath(filter.getName(), root, rootClazz);
        if (filter.getValue() == null) {
            return filterPath.isNull();
        }
        Y filteredObject = Util.getFilteredObject(filter.getName(), filter.getValue(), columnClazz);
        if (filter.isCaseInsensitive() && filteredObject.getClass().equals(String.class)) {
            return biFunction.apply((Expression<Y>) cb.lower((Expression<String>) filterPath),
                    (Y) lower(filteredObject, filter.getLocale()));
        } else {
            return biFunction.apply(filterPath, filteredObject);
        }
    }

    /**
     * @param value
     * @param localeStr
     * @return
     */
    private static String lower(Object value, String localeStr) {
        return value.toString().toLowerCase(getLocale(localeStr));
    }

    public static <T, Z extends Object> Class<Z> getFilterColumnClass(Class<T> clazz, String column) throws NoSuchFieldException {
        Class<? extends Object> acc = clazz;
        for (String s : column.split("\\.")) {
            acc = getFieldClass(acc, s);
        }
        Class<Z> reduce = (Class<Z>) acc;
        if (reduce == null) {
            throw new NoSuchFieldException(column);
        }
        return Primitives.wrap(reduce);
    }

    /**
     * construct filter value according to filter name attribute
     *
     * @param filterValue
     * @param columnClazz
     * @param <Y>
     * @return
     */
    public static <Y> Y getFilteredObject(String columnName, Object filterValue, Class<Y> columnClazz) {
        try {
            if (isJPAEntity(columnClazz)) {
                Optional<Field> idField = getIdField(columnClazz);
                if (idField.isPresent()) {
                    return getObjectBySettingId(filterValue, columnClazz, idField.get());
                }
            }
            Optional<Constructor<?>> first = Arrays.stream(columnClazz.getDeclaredConstructors())
                    .filter(c -> c.getParameterCount() == 1 &&
                            Primitives.wrap(c.getParameterTypes()[0]).isAssignableFrom(Primitives.wrap(filterValue.getClass())))
                    .findFirst();
            if (first.isPresent()) {
                return (Y) first.get().newInstance(filterValue);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to construct filtered object for filter name :{}", filterValue, e);
        }
        LOGGER.warn("Unable to construct filtered object for filter name :{}. Filter value is :{}"
                , columnName
                , filterValue);

        return (Y) filterValue;
    }

    /**
     * Constructs object by setting @Id field of the object
     *
     * @param filterValue
     * @param columnClazz
     * @param idField
     * @param <Y>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private static <Y> Y getObjectBySettingId(Object filterValue, Class<Y> columnClazz, Field idField)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Y instance = columnClazz.getDeclaredConstructor().newInstance();
        //replace with canAcces on Java 9
        boolean accessible = idField.isAccessible();
        idField.setAccessible(true);
        idField.set(instance, filterValue);
        idField.setAccessible(accessible);
        return instance;
    }

    /**
     * Constructs filter path
     *
     * @param columnName
     * @param root
     * @param rootClazz
     * @param <X>
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     */
    public static <X, T> Path<X> getFilterPath(String columnName, Root<T> root, @Nonnull Class<T> rootClazz) throws NoSuchFieldException {
        Path<?> currentPath = root;
        Class<?> currentClazz = rootClazz;
        boolean collection = false;

        for (String parsed : columnName.split("\\.")) {
            if (!collection) {
                collection = Util.isCollection(currentClazz, parsed);
            }
            currentClazz = Util.getFieldClass(currentClazz, parsed);
            currentPath = collection ? getJoinPath(currentPath, currentClazz, parsed) : currentPath.get(parsed);
        }
        return (Path<X>) currentPath;
    }

    /**
     * @param currentPath
     * @param currentClazz
     * @param parsed
     * @param <X>
     * @param <Y>
     * @return
     */
    private static <X, Y> Path<X> getJoinPath(Path<Y> currentPath, Class<?> currentClazz, String parsed) {
        Path<X> result;
        if (isAttribute(Primitives.wrap(currentClazz))) {
            result = currentPath.get(parsed);
        } else if (From.class.isAssignableFrom(currentPath.getClass())) {
            result = ((From) currentPath).join(parsed, JoinType.INNER);
        } else {
            result = ((ListJoin) currentPath).join(parsed);
        }
        return result;
    }

    /**
     * @param currentClazz
     * @return
     */
    private static boolean isAttribute(Class<?> currentClazz) {
        return currentClazz.equals(String.class) || Primitives.isWrapperType(currentClazz);
    }

    /**
     * @param clazz
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    private static Class<?> getFieldClass(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> foundClazz = null;
        try {
            foundClazz = getFieldClass(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                foundClazz = getFieldClass(clazz.getSuperclass(), fieldName);
            }
        }
        if (foundClazz == null) {
            throw new NoSuchFieldException(fieldName + ".Field does not exist.");
        }
        return foundClazz;
    }

    /**
     * returns class of the field
     *
     * @param field
     * @return
     */
    private static Class<?> getFieldClass(Field field) {
        Class<?> foundClazz;
        foundClazz = field.getType();
        if (Iterable.class.isAssignableFrom(foundClazz) &&
                field.getGenericType() instanceof ParameterizedType) {
            Type[] fieldArgTypes = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            if (fieldArgTypes.length > 0) {
                foundClazz = (Class<?>) fieldArgTypes[0];
            }
        }
        return foundClazz;
    }

    /**
     * checks field is collection type
     * <p>
     *
     * @param clazz
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    private static boolean isCollection(@Nonnull Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            Class<?> foundClazz = field.getType();
            if (Iterable.class.isAssignableFrom(foundClazz)) {
                return true;
            }
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null)
                getFieldClass(clazz.getSuperclass(), fieldName);
        }
        return false;
    }

    /**
     * @param clazz
     * @return true on condition class is jpa entity
     */
    private static boolean isJPAEntity(Class<?> clazz) {
        Optional<Annotation> first = Arrays.stream(clazz.getDeclaredAnnotations())
                .filter(a -> a.annotationType().equals(Entity.class) || a.annotationType().equals(MappedSuperclass.class))
                .findFirst();
        return first.isPresent();
    }

    /**
     * return @Id annotated field
     *
     * @param clazz
     * @return id field of the jpa entity
     */
    private static Optional<Field> getIdField(Class<?> clazz) {
        Optional<Field> first = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Arrays.stream(f.getDeclaredAnnotations())
                        .anyMatch(a -> a.annotationType().equals(Id.class)))
                .findFirst();
        if (first.isPresent()) {
            return first;
        } else {
            //search id field in super class
            return !clazz.equals(Object.class) ? getIdField(clazz.getSuperclass()) : Optional.empty();
        }
    }

    /**
     * Java 8 support
     *
     * @param obj
     * @param defaultObj
     * @param <T>
     * @return
     */
    public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : Objects.requireNonNull(defaultObj, "defaultObj");
    }

    /**
     * retrieves locale for language tag
     * returns default on condition localeStr is empty
     * <p>
     *
     * @param localeStr
     * @return
     */
    public static Locale getLocale(String localeStr) {
        return isNullOrEmpty(localeStr) ? Locale.getDefault() : Locale.forLanguageTag(localeStr);
    }

    /**
     * checks string null or empty
     * <p>
     *
     * @param localeStr
     * @return
     */
    public static boolean isNullOrEmpty(String localeStr) {
        return localeStr == null || localeStr.trim().length() == 0;
    }
}
