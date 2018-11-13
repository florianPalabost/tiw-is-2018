Universit� Claude Bernard Lyon 1 � M2 TIW � Intergiciels et Services

# TP Angular

## Objectifs p�dagogiques

- D�couvrir le framework Angular
- Savoir g�n�rer, configurer et d�velopper un projet et diff�rents �l�ments (composants, services...)
- Faire une premi�re application c�t� client qui interroge un serveur REST

## Pr�ambule

Dans ce TP, vous allez utiliser [Angular](https://angular.io/) pour r�aliser une application de r�servation de places pour le cin�ma. Vous utiliserez une version fonctionnelle de votre TP en Spring c�t� serveur, en veillant � ce qu'elle r�ponde bien aux m�thodes GET, PUT, POST et DELETE, en fonction des ressources pour lesquelles elle est interog�e.

Attention : vous devez avoir configur� votre serveur pour qu'il accepte les requ�tes en CORS, car l'application front ne sera pas servie par le m�me serveur.

## Structure du projet

Utilisez la CLI comme indiqu� en cours pour g�n�rer le projet.

## Consultation

Utilisez la CLI comme indiqu� en cours pour g�n�rer un service pour les requ�tes HTTP et un composant pour la consultation de chaque type de ressource (Film, Seance). Faites en sorte que Seance affiche le nombre de places disponibles, ainsi que le prix.

## R�servation

Dans un premier temps, toutes les r�servations seront accept�es, dans la limite des places disponibles.

- Faites un composant permettant de r�server, pour un utilisateur donn�, une ou plusieurs places
- Faites-en un autre permettant de modifier une r�servation
- Faites-en un troisi�me permettant de l'annuler

## Enrichissement

&Agrave; l'aide de l'URL dans la description des films, envoyez une nouvelle requ�te, et ajoutez ses r�sultats au composant de consultation.

To be continued...