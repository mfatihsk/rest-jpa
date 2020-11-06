insert into CITY(ID, NAME, CODE) values ('6', 'Ankara', '06');
insert into CITY(ID, NAME, CODE) values ('15', 'Burdur', '15');
insert into CITY(ID, NAME, CODE) values ('34', 'Istanbul', '34');
insert into CITY(ID, NAME, CODE) values ('35', 'Izmir', '35');


insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-1', '6', 'Bilkent Vakif Binası 1', 'Z03-1');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-2', '6', 'Bilkent Vakif Binası 2', 'Z03-2');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-3', '35', 'Bilkent Vakif Binası 3', 'Z03-3');

insert into EMPLOYEE(ID,NAME, SURNAME, HEIGHT, BIRTH_DATE,TITLE,OFFICE_ID) VALUES ('E1','Fatih','Isik',177,{ts '1984-02-01 01:01:01.01'},'ENGINEER','office-1');
insert into EMPLOYEE(ID,NAME, SURNAME, HEIGHT, BIRTH_DATE,TITLE,OFFICE_ID) VALUES ('E2','Mehmet','Isik',177,{ts '1980-01-01 01:01:01.01'},'ENGINEER','office-2');
insert into EMPLOYEE(ID,NAME, SURNAME, HEIGHT, BIRTH_DATE,TITLE,OFFICE_ID) VALUES ('E3','Murat','Catal',177,{ts '1986-01-01 01:01:01.01'},'BOSS','office-3');


insert into ADDRESS(ID,FULL_ADDRESS, CITY_ID, EMPLOYEE_ID) VALUES ('A1','Fatih Memleket','15','E1');
insert into ADDRESS(ID,FULL_ADDRESS, CITY_ID, EMPLOYEE_ID) VALUES ('A2','Fatih Ankara Altindağ','6','E1');
insert into ADDRESS(ID,FULL_ADDRESS, CITY_ID, EMPLOYEE_ID) VALUES ('A3','Fatih Koy Cebis','15','E1');

insert into ADDRESS(ID,FULL_ADDRESS, CITY_ID, EMPLOYEE_ID) VALUES ('B1','Mehmet Memleket','15','E2');
insert into ADDRESS(ID,FULL_ADDRESS, CITY_ID, EMPLOYEE_ID) VALUES ('B2','Mehmet Ankara Cebeci','6','E2');

insert into ADDRESS(ID,FULL_ADDRESS, CITY_ID, EMPLOYEE_ID) VALUES ('C1','Mustafa İş','35','E3');

insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-4', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-5', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-6', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-7', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-8', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-9', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-10', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-11', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-12', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-13', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-14', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-15', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-16', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-17', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-18', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-19', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-20', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-21', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-22', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-23', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-24', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-25', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-26', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-27', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-28', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-29', '6', '', 'xxx');
insert into OFFICE(ID, CITY_ID, NAME, CODE) values ('office-30', '6', 'BURDUR ZEYTUNI', 'xxx');
