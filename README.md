# TIW1 - Intergiciels et Services, année 2018-2019
## Florian PALABOST & Alper EKMEKCI 


### Routes H2
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
- ajout de jwt pour l'authentification
- ajout de postgres comme SGBD pour le service des reservations

### problèmes 
- les fichiers json devraient etre disponibles dans le target mais ils ne le sont pas
  - les mettre dans le meme repertoire que tp3.jar
