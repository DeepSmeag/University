--Creare db
--user master
--drop database Catering
--go

--create database Catering
--go
--use Catering

-- Scripturi creare tables dupa reformare

--create table Angajat(
--id int primary key identity,
--nume varchar(50),
--prenume varchar(50))

--create table Clienti(
--id int primary key identity,
--nume varchar(50),
--prenume varchar(50))

--create table Curieri
--(
--id int primary key identity,
--nume varchar(50),
--prenume varchar(50),
--telefon varchar(10),
--masina varchar(7)
--)

alter table Comenzi
add idAngajat int foreign key references Angajat(id)

create table ListaProduse (
	idComanda int foreign key references Comenzi(numar),
	produs varchar(50) foreign key references ProduseAlimentare(denumire),
	cantitate int,
	pret money,
	constraint pk_idListaProduse primary key (idComanda, produs)
)


-- Punct 2
select produs, cantitate, ProduseAlimentare.um, LP.pret, LP.pret * cantitate as valoare 
from ListaProduse LP inner join ProduseAlimentare 
on LP.produs = ProduseAlimentare.denumire
where LP.idComanda = 1


-- Creare or view

--alter view viewCurieri as 

--select Curieri.nume 'Nume curier', Curieri.prenume 'Prenume curier', (0.25 * 
--    sum(LP.cantitate * LP.pret)) 'Suma plata'
--from Curieri 
--inner join Comenzi on (Comenzi.idCurier = Curieri.id)
--inner join ListaProduse LP on (LP.idComanda = Comenzi.numar)
--where year(Comenzi.dataComanda) = year(getdate()) 
--    and month(Comenzi.dataComanda) = month(getdate()) 
--	and 'Suma plata' > 100
--group by Comenzi.idCurier, Comenzi.numar, Curieri.nume, Curieri.prenume
