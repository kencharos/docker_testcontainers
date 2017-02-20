CREATE TABLE DIV(id int not null,  name varchar(20) not null, PRIMARY KEY(id));
CREATE TABLE PRODUCT(id int not null,  name varchar(20) not null, price int,
    div_id int not null, PRIMARY KEY(id), FOREIGN KEY(div_id) REFERENCES DIV (id));