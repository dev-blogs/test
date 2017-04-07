DROP TABLE IF EXISTS CONTACT;

use testdb;

create table warehouses (
	id int not null auto_increment,
	address varchar(255),
	primary key (id)
);

create table items (
	id int not null auto_increment,
	name varchar(255),
	warehouse_id int not null,
	primary key (id)
);

insert into warehouses (address) values ('ul. one, 5');
insert into warehouses (address) values ('ul. two, 4');
insert into warehouses (address) values ('ul. tree, 7');
insert into warehouses (address) values ('ul. four. 7');

insert into items (name, warehouse_id) values ('red table', 1);
insert into items (name, warehouse_id) values ('green chair', 1);
insert into items (name, warehouse_id) values ('red spoon', 2);
insert into items (name, warehouse_id) values ('green fork', 2);
insert into items (name, warehouse_id) values ('brown blinds', 3);
insert into items (name, warehouse_id) values ('white table', 5);

use testdb1;

create table contact (
	id int not null auto_increment,
	first_name varchar(60) not null,
	last_name varchar(60) not null,
	birth_date date,
	version int not null default 0,
	unique uq_contact_1 (first_name, last_name),
	primary key (id)
);

insert into contact (first_name, last_name, birth_date) values ('Clarence', 'Ho', '1980-07-30');
insert into contact (first_name, last_name, birth_date) values ('Scott', 'Tiger', '1990-11-02');
insert into contact (first_name, last_name, birth_date) values ('John', 'Smith', '1964-02-28');