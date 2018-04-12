DROP TABLE IF EXISTS public.employee;
CREATE TABLE employee
(
    user_id serial primary key,
    last_name VARCHAR(40) not null,
    first_name varchar(30) not null,
	country varchar(60) not null,
	date_of_birth date not null
);
INSERT INTO employee(last_name,first_name,country,date_of_birth) VALUES('Tri','Nguyen','VN','1995-09-22');