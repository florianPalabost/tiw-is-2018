# TP2: Conteneurs d'objets et inversion de contrôle

## Objectifs pédagogiques

Mettre en pratique différents patterns (IoC, Contexte, Annuaire, Object pool...) afin de mieux comprendre le fonctionnement d'un framework. Mettre en place un outil configurable et capable de gérer le cycle de vie de ses composants. 

## Utilisation du projet "réservation de places de cinéma"

Dans ce TP, vous allez transformer cette application comme si elle s'exécutait côté serveur, et petit à petit, construire les différents éléments d'un framework autour d'elle.

Voici la façon dont elle fonctionnera :

- Le contenu qui vous a été donné dans le répertoire `metier-base` ainsi que les DAOs que vous avez développés au TP1 se trouveront côté serveur.
- Dans ce TP, le client sera simulé par les tests que vous écrirez. Ces tests adresseront directement le serveur et non les classes métier.

Dans ce TP, vous aurez 3 types de données à manipuler avec chacune une durée de vie différente :
1) Les salles, qui font partie intégrante du cinéma, et jusqu'à nouvel ordre, ne changeront pas
2) Les films et les séances, qui peuvent être mis à jour régulièrement (toutes les semaines) par les employés du cinéma
3) Les réservations, qui peuvent être créées ou annulées par les clients à tout moment.

## Premières manipulations

Créez un package `serveur` dans lequel vous placerez une classe `Serveur`. Cette classe interfacera les clients avec l'ensemble des objets en lien avec le cinéma. Dans un premier temps, cette classe aura pour rôle d'instancier les objets qui seront passés à la création du cinéma.

### Injection de la liste des salles

- Faites en sorte que le constructeur de `Cinema` prenne un autre paramètre qui sera de type `List<Salle>` et remplisse automatiquement sa liste de salles à partir de celle-ci.
- Enlevez les salles du fichier de données `mon-cinema.json` (au besoin, modifiez vos DAOs en conséquence), et stockez-les dans un autre fichier.
- Faites en sorte que `Serveur` lise ce fichier, crée une liste de salles à partir du DAO le concernant, et l'injecte dans le constructeur de `Cinema`.
- Vous pouvez désormais supprimer les méthodes d'ajout et de suppression de salles.
- Modifiez vos tests en conséquence et exécutez-les.

### Gestion de la programmation (données stables)

Les films et les séances seront lus directement par le cinema sur les supports de persistance à l'initialisation :

- Faites un DAO commun pour les films et les séances, et ajoutez-le au constructeur de `Cinema`, de façon à intégrer toute la logique de persistance du côté du serveur.
- Faites en sorte que Faites en sorte que `Serveur` instancie ce DAO pour pouvoir l'injecter au cinéma. 
- Modifiez vos tests en conséquence et exécutez-les.

### Gestion des réservations

- Faites en sorte que le constructeur de `Cinema` prenne également en paramètre un DAO (relationnel ?) pour les réservations à la fin de son initialisation, qui sera initialisé par le serveur.
- Testez.

### Autres manipulations

- Utilisez le pattern DTO pour simplifier l'interface de `Cinema` et permettre d'ajouter et supprimer facilement une séance.
- Vous pouvez aussi rajouter les getters nécessaires pour permettre à `Cinema` de renvoyer les informations sur ses attributs.
- Rajoutez une méthode dans `Serveur`qui retourne l'instance de `Cinema` créée.
- Testez.

&Agrave; ce stade, vous devez avoir un serveur capable de retourner un cinéma fonctionnel.

## 1. Inversion de contrôle

Vous allez maintenant mettre en place un conteneur et remplacer toute la logique d'injection de dépendances "directe" que vous avez créée à la section précédente par un mécanisme de résolution de dépendances. Pour cela :

- Créez un conteneur (voir indications ci-dessous)
- Placez le `Cinema` et tous les objets dont il a besoin pour s'instancier dans ce conteneur.

### Indications

- Conteneur :
La classe Serveur instanciera un conteneur qui sera basé sur l'outil [PicoContainer](http://picocontainer.com/).
Alternativement : 
  - Solution à préférer : vous ajouterez la [dépendance Maven sur la version 2.15 de Picocontainer](https://search.maven.org/artifact/org.picocontainer/picocontainer/2.15/jar)
  - Défaut : vous téléchargerez la dernière version du jar [sur le site](http://central.maven.org/maven2/org/picocontainer/picocontainer/2.15/picocontainer-2.15.jar) ou la version disponible en local [ici](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/TP1/picocontainer-2.15.jar.)

  La doc est disponible [sur le site](http://picocontainer.com/) et une Javadoc de la version 2.14.3 [en local](https://perso.liris.cnrs.fr/lionel.medini/enseignement/CAHD/TP1/picocontainer-2.14.3-javadoc.jar). Commencez par lire la première page d'introduction à l'utilisation de cet outil, située [ici](http://picocontainer.com/introduction.html).
- Arbre de dépendances (pour l'instant, il est assez plat) :
  > Cinema -> string contenant son nom

  > Cinema -> List<Salle>

  > Cinema -> ProgrammationDao (films et séances)

  > Cinema -> ReservationDao

  Remplacez l'instance de `ProgrammationDao` créée "à la main" par le serveur par la classe elle-même. On suppose ici que son constructeur prend une string en paramètre (le nom du fichier). Alternativement :
  - utilisez le même string pour déduire le nom du ficher et nommer le cinéma
  - [Désambiguïsez](http://picocontainer.com/disambiguation.html) les noms dans les paramètres des constructeurs pour que PicoContainer soit capable de résoudre le référentiel de dépendances. 
- Méthodes de la classe `Cinema` : 
  - méthodes de gestion du cycle de vie : faites en sorte que la classe `Cinema` implémente l'interface Startable et ajoutez-y les méthodes requises ; dans la méthode start(), rajoutez un affichage indiquant que le serveur a démarré, le type de classe d'implémentation du `Cinema` (aide : utilisez l'API Reflection) et l'instance du DAO qu'il utilise pour accéder aux données (aide : méthode toString() de l'instance). L'objectif est d'obtenir un affichage du style :

  Composant `Cinema` démarré. Objet d'accès aux données : fr.univlyon1.m2tiw.tiw1.metier.cinema.dao.ProgrammationDAO@95c083 
- Méthodes de la classe `Serveur` : 
  - constructeur : il instanciera un `DefaultPicoContainer`, puis y rajoutera les composants avec des dépendances entre eux. Il récupèrera ensuite le composant `Cinema` instancié par le conteneur, le stockera dans une variable globale et appellera sa méthode start().
  - méthode (provisoire) de service : getCinema(), renvoyant au client une référence vers l'instance de `Cinema`.

À ce stade, vous avez inversé le contrôle de vos objets métier en les plaçant dans un serveur (i.e. un framework) qui se charge d'instancier, de gérer et d'utiliser ces objets.

## 2. Isolation et uniformisation des objets côté serveur

### 2.1. Isolation

Bien entendu, vous ne pouvez pas laisser le client accéder directement à l'instance du cinéma créée dans le conteneur. Pour cela, vous allez implémenter le paradigme requête-réponse :

- Modifiez la méthode de service du serveur pour qu'elle soit plus générique ; par exemple :

  `public String processRequest(String commande, HashMap<String, String> parametres);`

  où les éléments de la HashMap représentent les paires nom / valeur des paramètres des requêtes
- Dans le cinéma, passez les méthodes add, remove, get... en privé, et créez une méthode publique `process`, qui appellera chacune de ces trois méthodes en fonction de la commande
- Modifiez vos tests pour qu'ils n'appellent plus que la méthode `processRequest()` du serveur
- Renommez la classe `Serveur` en `ServeurImpl`, extrayez l'interface de service du serveur (que vous appellerez `Serveur`) et faites en sorte que les clients (les tests) ne connaissent plus que cette interface.

Remarquez que la classe `ServeurImpl` masque désormais complètement l'implémentation du traitement des requêtes par les objets métier. Il suffit au client de connaître son API pour utiliser le cinéma.

### 2.2. Uniformisation

To be continued...