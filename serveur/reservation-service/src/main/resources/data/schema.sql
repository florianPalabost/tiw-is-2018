create table reservation(
  id INTEGER PRIMARY KEY,
  prenom VARCHAR(255),
  nom VARCHAR (255),
  email VARCHAR(255),
  paye INTEGER,
  seanceid varchar (255) not null
);
create sequence hibernate_sequence;
