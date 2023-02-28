use master 
drop database MusicShop
go

create database MusicShop;
go
use MusicShop;
go


create table Customers (
id int PRIMARY KEY identity, 
name varchar(255) not null unique,
password varchar(255) not null,
email varchar(255) not null unique,
phone char(10) not null unique,
isMusician bit not null,
birthdate date not null,
CONSTRAINT chk_phone CHECK (phone  like '[0-9]{10}')
);
go

create table Admins (
id int primary key identity,
name varchar(255) not null unique,
email varchar(255) not null unique,
since date not null default getdate()
);
go

create table ItemsCategories (
id int primary key identity,
category varchar(255) not null unique,
);
go

create table OrderStatus (
id int primary key identity,
status varchar(255) not null unique
);
go

create table Manufacturers(
id int primary key identity,
name varchar(255) not null unique,
since date not null default getdate(),
);
go

create table Inventory (
code varchar(255) primary key,
name varchar(255) not null unique,
category varchar(255) foreign key references ItemsCategories(category) on update cascade,
manufacturer varchar(255) foreign key references Manufacturers(name) on update cascade,
priceUnit int not null,
CONSTRAINT chk_price CHECK (priceUnit >0),
stock int not null,
CONSTRAINT chk_stock CHECK (stock >0),
);
go

create table Orders (
id int primary key identity,
dateCreated date not null default getdate(),
status varchar(255) foreign key references OrderStatus(status) on update cascade,
comments varchar(255),
CONSTRAINT dateCreated check (dateCreated < getdate())
);
go

create table OrdersList (
orderId int foreign key references Orders(id),
customerId int foreign key references Customers(id),
itemCode varchar(255) foreign key references Inventory(code),
quantity int,
CONSTRAINT chk_quantityOrdersList check (quantity>0),
CONSTRAINT pk_orderslist primary key (orderId,customerId,itemCode)
);
go

create table Promotions(
id int primary key identity,
itemCode varchar(255) unique foreign key references Inventory(code),
discountPercent float,
CONSTRAINT chk_discountPercent check (discountPercent >0.0 and discountPercent <100.0)
);
go

create table StockOrders (
id int primary key identity,
dateCreated date not null default getdate(),
adminId int foreign key references Admins(id),
itemCode varchar(255) foreign key references Inventory(code),
quantity int,
CONSTRAINT chk_quantityStockOrder check (quantity > 0)
);
go
