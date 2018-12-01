-- comptes
create table compte
(
  id    integer primary key,
  solde double
);

-- autorisations de prélèvements
create table autorisations
(
  parent       integer references compte (id),
  destinataire integer references compte (id),
  maximum      double,
  primary key (parent, destinataire)
);
