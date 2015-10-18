


CREATE TABLE  Reader (
  idReader INTEGER NOT NULL PRIMARY KEY IDENTITY(1,1),
  name VARCHAR(25) NOT NULL,
  surname VARCHAR(25) NOT NULL,
  join_date date NOT NULL,
  extra_points integer default 0

 
 ) ;
CREATE TABLE  Book (

  idBook INTEGER NOT NULL PRIMARY KEY IDENTITY(1,1),
  
  title VARCHAR(70) NOT NULL,
  relase_date Date not null,
  relase VARCHAR(15) not null

);



CREATE TABLE  Author (

  idAuthor INTEGER NOT NULL PRIMARY KEY IDENTITY(1,1),
  
  name VARCHAR(70) NOT NULL,
  surname VARCHAR(30) NOT NULL
);
CREATE TABLE  BookAuthors (

  idAuthor INTEGER NOT NULL REFERENCES Author(idAuthor),
  idBook INTEGER NOT NULL REFERENCES Book(idBook),
	Primary Key (idAuthor, idBook)
);

CREATE TABLE  Hiring (
  idHiring INTEGER NOT NULL IDENTITY(1,1) PRIMARY KEY ,
 idBook INTEGER NOT NULL REFERENCES Book(idBook),
 idReader INTEGER NOT NULL REFERENCES Reader(idReader),
 hire_date DATE NOT NULL


);




