-- comptes
create table compte
(
  id    integer primary key,
  solde double
);

-- autorisations de prélèvements
create table autorisation
(
  id  integer primary key,
  parent       integer references compte (id),
  destinataire integer references compte (id),
  maximum      double
);

-- sequence pour les comptes
create sequence compte_sequence;

-- sequence pour les autorisations
create sequence autorisation_sequence;