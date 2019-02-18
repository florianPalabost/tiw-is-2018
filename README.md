# TIW1 - Intergiciels et Services, année 2018-2019
## Florian PALABOST & Alper EKMEKCI 

### Liens forge + présentation (pour l'architecture de l'application)
- https://forge.univ-lyon1.fr/p1301169/tiw1-is-2018.git
- https://docs.google.com/presentation/d/1mfLmJ3mwxyhaLRKtUY6JRRP-QqXJ7_qcHM9jVMGwul4/edit?usp=sharing

### Lancement du projet 
Soit avec le script start.sh --> ./start.sh 
Sinon manuellement : 
docker-compose down --rmi all
cd serveur
mvn clean install (-DskipTests) --> pour aller plus vite
cp metier-base/MonCinema/*.json tp3/target/
cd ..
docker-compose up --build (-d pour reprendre la main) 

### Avancement
Globalement les fonctionnalités demandandées ont bien été réalisée sauf pour le tp10, donc il n'y a pas kubernates et les tests de performance.
Pour le TP4, nous avons implémenté des reqûetes cors vers l'api imdb pour avoir des informations sur les films.
La partie Kubernate n'a pas pu être faite car on arrivait pas à push nos images et nous avons trouvé une solution beaucoup trop tard pour l'utiliser (utilisation de docker hub et ne pas passer par la forge pour récupérer les images).

### SoapUI TP6 - TP7
- banque : autorisation - prèlevement ok
- réservation : reserver - annuler ok
- Les intercepteurs ont été réalisé (test à faire avec soapUI).

### RabbitMQ TP8
le serveur tourne dans un docker ainsi que les clients.
On a bien les logs sur les informations du compte
Modification du boolean paye si le paiement a bien été réalisé (a tester avec soapui)
Commencement d'une borne de tickets (spring boot application): par manque de temps elle ne gère que la reservation, le paiement bug à cause de la requête cors(on a pas eu le temps d'approfoncdir le sujet sur pourquoi celle-ci ne passe pas alors que les autres oui).

### Microservices TP9
Nous avons décomposé notre application en différents services(voir architecture de la présentation)
Reverse proxy que pour le front, pour le back, redirection vers /login (page blanche) et on a préféré l'enlever par manque de temps et pour que le back reste fonctionnel.
docker postgres réalisé avec un volume pour la persistence des données (même si destruction des images et des dockers on retrouve nos données puisque stocké sur l'hôte)  
docker-compose pour l'orchestration de nos dockers (avec des dockerfile dans chaque service)
### Routes H2 databases
admin
http://localhost:8080/console
  url : jdbc:h2:mem:mytestdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

banque
http://localhost:8082/console
  url : jdbc:h2:mem:banquedb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

jwt user
http://localhost:8081/h2-console
  url : jdbc:h2:mem:mytestdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

### Refonte du projet en microservices 
- transformation de reservation en un service
- ajout de jwt pour l'authentification (pas de lien avec le front ...)
- ajout de postgres comme SGBD pour le service des reservations

### problèmes 
- gitlab runner : impossibilité de build et push les images du projet 
- les fichiers json devraient etre disponibles dans le target mais ils ne le sont pas
- les mettre dans le meme repertoire que tp3.jar
