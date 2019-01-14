Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP Microservices

## Objectifs pédagogiques

- Concevoir et construire une architecture applicative à base de microservices.
- Approfondir vos connaissances sur Docker

## Outils

Dans ce TP, vous utiliserez l'infrastructure OpenStack et les VMs dont l'IP est sur Tomuss. Les autres images dont vous partirez sont à aller chercher sur le [Docker hub](https://hub.docker.com/), de même que les docs correspondantes.

Vous utiliserez [Apache Bench](http://httpd.apache.org/docs/2.2/programs/ab.html) pour tester votre application depuis une autre machine sur le réseau. Pour l'installer en Debian/Ubuntu : <span class="code">sudo apt-get install apache2-utils</span>. Pour tester : <span class="code">ab -n 10000 -c 10 http://adresse-à-tester</span>.

## Indications

Vous trouverez beaucoup d'indications utiles pour déployer vos containers sur l'architecture OpenStack dans les TPs [Swarm 1](http://perso.univ-lyon1.fr/fabien.rico/site/cloud:tp_swarm) et [Swarm 2](http://perso.univ-lyon1.fr/fabien.rico/site/cloud:tp_swarm2) de TIW7.

## Préambule

Dans ce TP, vous allez reprendre votre application cinéma et la décomposer progressivement en microservices. Pour cela, vous devez disposer d'une version :

*   tournant dans un framework (Spring ou OSGi)
*   avec des composants `CinemaRessourceXxx` séparés et une interface standardisée pour y accéder
*   avec un interface front web (Angular)
*   avec la possibilité de passer facilement d'un support de persistance en XML à une BD (cf. question "Spring Data" du TP Spring, exemples de fichiers de persistence [là](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/Docker/persistence.xml) et [là](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/Docker/persistence_ref_contexte.xml))

### Infrastructure du TP

Chaque binôme se verra affecté une machine virtuelle avec docker préinstallé.

## Mise en place de l'application

Dans cette section, vous allez "dockeriser" votre application et la faire tourner dans plusieurs conteneurs.

1.  Récupérez une version complète de votre application - avec la persistence dans un fichier XML et le client dans une interface web - et faites-la tourner dans un conteneur. Vous aurez besoin de mapper les fichiers de configuration et de déploiement avec des emplacements spécifiques du système de fichiers de la machine hôte ; pour cela, utilisez des [data volumes](https://docs.docker.com/userguide/dockervolumes/). Faites la redirection de ports pour pouvoir tester depuis une machine de TP.
2.  Ajoutez un conteneur nginx en front et configurez nginx pour qu'il redirige les requêtes sur le port d'écoute du conteneur applicatif (exposez le port d'écoute d'nginx sur la machine hôte et supprimez l'exposition de celui du conteneur applicatif).
3.  Créez un nouveau conteneur avec le SGBD de votre choix (postgreSQL, mySQL...) et modifiez la persistence de l'application pour qu'elle fonctionne avec un entity manager qui utilise ce SGBD. Pour ne pas exposer le SGBD sur le réseau, faites communiquer les conteneurs avec un [overlay network](https://docs.docker.com/network/overlay/). 
    Remarques :
    *   Vous pouvez utiliser un autre conteneur, par exemple contenant un PHPMyAdmin, pour configurer votre base avant de la relier au reste de l'application, mais le plus simple est de déclarer la configuration dans les variables d'environnement d'un <span class="code">Dockerfile</span>
    *   En toute logique, vous ne devriez pas stocker la BD de votre application dans un conteneur, mais utiliser un data volume pour mapper les éléments de votre SGBD qui servent à conserver les données sur le système de fichier de la machine hôte...
    *   Penser à modifier la configuration de la persistence pour accéder à la base de données conteneurisée.
    
Remarque : plus vous mettrez de config dans des <span class="code">Dockerfile</span> plutôt que dans des commandes <span class="code">docker run</span>, plus cela vous simplifiera la vie pour la question suivante...

### Configuration de l'application

Vous allez maintenant utiliser [Docker compose](https://docs.docker.com/compose/) pour déclarer la structure globale de votre application et en automatiser le démarrage. 

Créez un fichier <span class="code">docker-compose.yml</span> qui regroupe toutes les commandes de lancement de vos conteneurs, ainsi que les ports et les overlay networks. Testez, par exemple avec SoapUI et avec votre client Angular.

## Séparation des tâches dans plusieurs conteneurs applicatifs

Actuellement, la totalité du métier de votre application s'exécute dans le même conteneur. Si celle-ci est soumise à de nombreuses requêtes, ce conteneur peut être répliqué mais il occupera nécessairement beaucoup d'espace, par rapport à la tâche qu'il remplira. Vous allez "morceler" votre application dans plusieurs conteneurs, que vous pourrez répliquer indépendamment les uns des autres. &Agrave; chaque étape, vous redirigerez les requêtes depuis le front sur les conteneurs adéquats.

*   L'application comporte deux processus métier principaux : le système de back-office et celui de réservation, qui sont indépendants et n'ont pas la même charge ni les mêmes besoins de scalabilité. Ils peuvent donc être placés dans des conteneurs différents. Cependant, ils partagent des données. Choisissez une solution pour qu'ils aient tous les deux accès à un support de persistance, et que ces supports (s'ils sont plusieurs) soient synchronisés entre eux.
*   De la même manière, les `CinemaRessourceXxx` peuvent s'exécuter dans des conteneurs différents. Pensez aux accès concurrents au support de persistance.
*   Enfin, les patterns dupliqués (DAO, contexte...) dans les différents conteneurs peuvent aussi être isolés dans des conteneurs différents. Vous pouvez également créer des [conteneurs ambassadeurs](http://docs.docker.com/engine/articles/ambassador_pattern_linking/) pour améliorer la communication entre les conteneurs sur différents hôtes.
    
Testez plusieurs configurations et tester leur performance avec apache bench.notez les performances pour chaque type de requête. 
Adoptez la plus performante d'entre elles.

Utilisez le repository docker de https://forge.univ-lyon1.fr (il faut l'activer pour votre dépôt) pour y stocker les images des différents conteneurs que vous avez créé.

## Kubernetes

À venir ...

