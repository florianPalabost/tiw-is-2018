insert into compte values(123456789, 200.0);
insert into compte values(234567890, 100.0);
insert into compte values(345678901, 1000.0);
insert into autorisation values (123456789, 234567890, 10.0);
insert into autorisation values (123456789, 345678901, 50.0);
alter sequence compte_sequence restart with (select max(id)+1 from compte);