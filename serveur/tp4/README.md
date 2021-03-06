Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP Angular

## Objectifs pédagogiques

- Découvrir le framework Angular
- Savoir générer, configurer et développer un projet et différents éléments (composants, services...)
- Faire une première application côté client qui interroge un serveur REST

### Ressources

Lien vers les cours :

- [TypeScript](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/revealJS/#TypeScript)
- [Angular](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/revealJS/#Angular)

## Préambule

Dans ce TP, vous allez utiliser [Angular](https://angular.io/) pour réaliser une application de réservation de places pour le cinéma. Vous utiliserez une version fonctionnelle de votre TP en Spring côté serveur, en veillant à ce qu'elle réponde bien aux méthodes GET, PUT, POST et DELETE, en fonction des ressources pour lesquelles elle est interogée.

Attention : vous devez avoir configuré votre application côté serveur pour qu'elle accepte les requêtes en CORS, car l'application front ne sera pas servie par le même serveur.

### Modification de la structure du projet

Il est recommandé de rajouter un niveau de structure dans l'arborescence des répertoires de votre projet et de :

- créer un répertoire `serveur` à la racine de votre projet et déplacer l'application en Spring dedans
- créer un répertoire `client` où vous placerez le code réalisé pendant ce TP

Remarque : vous devrez aussi modifier vos scripts GitLab CI pour qu'ils s'exécutent dans le répertoire `serveur`

## Initialisation

Utilisez la CLI comme indiqué en cours pour générer le projet.

## Consultation

Utilisez la CLI comme indiqué en cours pour générer un service pour les requêtes HTTP et un composant pour la consultation de chaque type de ressource (Film, Seance). Faites en sorte que Seance affiche le nombre de places disponibles, ainsi que le prix.

## Réservation

Dans un premier temps, toutes les réservations seront acceptées, dans la limite des places disponibles.

- Faites un composant permettant de réserver, pour un utilisateur donné, une ou plusieurs places pour une séance donnée
- Faites-en un autre permettant de modifier une réservation
- Faites-en un troisième permettant de l'annuler

## Enrichissement

&Agrave; l'aide de l'URL dans la description des films, envoyez une nouvelle requête, et ajoutez ses résultats au composant de consultation.
