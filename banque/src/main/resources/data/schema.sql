-- comptes
create table compte
(
  id    integer primary key,
  solde double
);

-- autorisations de prélèvements
create table autorisation
(
  parent       integer references compte (id),
  destinataire integer references compte (id),
  maximum      double,
  primary key (parent, destinataire)
);

-- sequence pour les comptes
create sequence compte_sequence;
