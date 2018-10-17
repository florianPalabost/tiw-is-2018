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

- Faites en sorte que le constructeur de `Cinema` prenne un autre paramètre qui sera de type `List<Salle>` et remplisse automatiquement sa map de salles à partir de celle-ci.
- Enlevez les salles du fichier de données `mon-cinema.json` (au besoin, modifiez vos DAOs en conséquence), et stockez-les dans un autre fichier.
- Faites en sorte que `Serveur` lise ce fichier, crée une liste de salles à partir du DAO le concernant, et l'injecte dans le constructeur de `Cinema`.
- Vous pouvez désormais supprimer les méthodes d'ajout et de suppression de salles.
- Modifiez vos tests en conséquence et exécutez-les.

### Gestion de la programmation (données stables)

Les films et les séances seront lus directement par le cinema sur les supports de persistance à l'initialisation :

- Faites un DAO commun pour les films et les séances, et ajoutez-le au constructeur de `Cinema`, de façon à intégrer toute la logique de persistance du côté du serveur.
- Faites en sorte que `Serveur` instancie ce DAO pour pouvoir l'injecter au cinéma. 
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

  ```
  Cinema -> string contenant son nom
  Cinema -> List<Salle>
  Cinema -> ProgrammationDao (films et séances)
  Cinema -> ReservationDao
  ```

  Remplacez l'instance de `ProgrammationDao` créée "à la main" par le serveur par la classe elle-même. On suppose ici que son constructeur prend une string en paramètre (le nom du fichier). Alternativement :
  - utilisez le même string pour déduire le nom du ficher et nommer le cinéma
  - [Désambiguïsez](http://picocontainer.com/disambiguation.html) les noms dans les paramètres des constructeurs pour que PicoContainer soit capable de résoudre le référentiel de dépendances. 

  - Méthodes de la classe `Cinema` : 
    - méthodes de gestion du cycle de vie : faites en sorte que la classe `Cinema` implémente l'interface Startable et ajoutez-y les méthodes requises ; dans la méthode start(), rajoutez un affichage indiquant que le serveur a démarré, le type de classe d'implémentation du `Cinema` (aide : utilisez l'API Reflection) et l'instance du DAO qu'il utilise pour accéder aux données (aide : méthode toString() de l'instance). L'objectif est d'obtenir un affichage du style :

  ```Composant Cinema démarré. Objet d'accès aux données : fr.univlyon1.m2tiw.tiw1.metier.cinema.dao.ProgrammationDAO@95c083```
  
  - Méthodes de la classe `Serveur` : 
    - constructeur : il instanciera un `DefaultPicoContainer`, puis y rajoutera les composants avec des dépendances entre eux. Il récupèrera ensuite le composant `Cinema` instancié par le conteneur, le stockera dans une variable globale et appellera sa méthode start().
    - méthode (provisoire) de service : getCinema(), renvoyant au client une référence vers l'instance de `Cinema`.

> À ce stade, vous avez inversé le contrôle de vos objets métier en les plaçant dans un serveur (i.e. un framework) qui se charge d'instancier, de gérer et d'utiliser ces objets.

Ne vous préoccupez pas pour l'instant des méthodes permettant de gérer les réservations.

## 2. Isolation et uniformisation des objets côté serveur

### 2.1. Isolation

Bien entendu, vous ne pouvez pas laisser le client accéder directement à l'instance du cinéma créée dans le conteneur. Pour cela, vous allez implémenter le paradigme requête-réponse :

- Modifiez la méthode de service du serveur pour qu'elle soit plus générique ; par exemple :

  `public String processRequest(String commande, HashMap<String, String> parametres);`

  où les éléments de la HashMap représentent les paires nom / valeur des paramètres des requêtes
- Dans le cinéma, passez les méthodes add, remove, get... en privé, et créez une méthode publique `process()`, qui appellera chacune de ces trois méthodes en fonction de la commande
- Modifiez vos tests pour qu'ils n'appellent plus que la méthode `processRequest()` du serveur
- Renommez la classe `Serveur` en `ServeurImpl`, extrayez l'interface de service du serveur (que vous appellerez `Serveur`) et faites en sorte que les clients (les tests) ne connaissent plus que cette interface.

> Remarquez que la classe `ServeurImpl` masque désormais complètement l'implémentation du traitement des requêtes par les objets métier. Il suffit au client de connaître son API pour utiliser le cinéma.

### 2.2. Uniformisation

Plutôt que d'avoir un objet `Cinema` qui répond à différentes requêtes, vous allez créer plusieurs objets sur le même modèle, mais traitant chacun un type de requête spécifique. Pour cela :
- Commencez par définir une interface et une classe abstraite reprenant les principales caractéristiques du cinema : dépendances, implémentation de `Startable` et méthode de service `process()`
- Créez les classes implémentant ce modèle et correspondant aux méthodes de service `addFilm()`, `removeFilm()`, `getFilm()`, `createSeance()` et `createReservation()`, etc. Vous regrouperez les fonctionnalités dans des classes représentant des ressources (et non des opérations), par exemple une classe `CinemaRessourceFilms` pour la mise à jour et la consultation de films, une classe pour la gestion des séances, et une pour accéder aux informations des salles. Pour cela, il faut que vous ajoutiez un paramètre supplémentaire (la notion de "méthode"), qui indique l'opération à réaliser sur la ressource.
- Modifiez le serveur pour que votre conteneur crée les composants correspondants aux instances de vos nouvelles classes
- Créez une méthode d' "aiguillage" des requêtes vers les instances de chacune de ces classes qui sera appelée par la méthode de service du serveur : la commande correspond au nom de la classe à appeler, comme un nom de ressource sur un serveur Web. Créez ensuite (et factorisez dans la classe abstraite), le mécanisme qui appellera la bonne méthode de la classe, en fonction de la valeur du paramètre "méthode" défini plus haut.

**Normalement, votre application ne doit pas fonctionner :** le container vous renvoie une liste vide à chaque opération et les instances des DAO sont différentes dans les messages d'initialisation des méthodes de gestion du cycle de vie.

Cela vient du fait que bien que les List, ProgrammationDao et ReservationDao soient des dépendances communes de tous les `CinemaRessourceXxx` du conteneur, par défaut, celui-ci résout les dépendances en instanciant un objet différent pour chaque instance de `CinemaRessourceXxx`. Toutefois, vous pouvez indiquer que vous souhaitez procéder autrement, c'est-à-dire qu'il "cache" les instances. Vous pouvez résoudre ce problème à deux niveaux :

1.	Au niveau du composant : en spécifiant la caractéristique "Cache" des composants que vous voulez cacher. Le plus simple est d'utiliser la méthode `as()` du conteneur, comme spécifié [ici](http://picocontainer.com/properties.html).
2.	Au niveau du conteneur : en spécifiant un [comportement](http://picocontainer.com/behaviors.html) global de type "Caching" pour tous les composants du conteneur dans le constructeur de celui-ci.

Si vous choisissez la seconde solution, les cinéma-ressources seront cachés également, et vous n'aurez plus besoin de les stocker dans une variable globale. Par ailleurs, comme indiqué dans le warning du début de la [page sur la gestion du cycle de vie des composants](http://picocontainer.com/lifecycle.html), les méthodes `start()`, `stop()`, etc. sont conçues pour fonctionner avec des composants cachés, et vous pourrez appelez directement la méthode `start()` du conteneur pour qu'il démarre tous les composants qui implémentent `Startable` en même temps...

> À ce stade, vous avez réalisé un outil équivalent à un conteneur de servlets. Il pourra fonctionner avec n'importe quel type de ressource (au sens ReST) utilisé par l'application du cinéma, effectuer différentes opérations (correspondant à des méthodes HTTP), à partir du moment où celle-ci est déclarée dans le serveur et correspond à une commande reconnue.

## 3. Création d'un contexte applicatif

Dans cette partie, vous allez rajouter un niveau d'indirection entre le conteneur (et ses composants) et les objets de type ProgrammationDAO. Pour cela, vous allez implémenter une classe qui permettra à chaque instance de `CinemaRessourceXxx` créée d'accéder à ce DAO en respectant le pattern Context présenté en cours. Cela permettra par exemple d'utiliser un DAO instancié à l'extérieur du serveur (cas d'une connexion à un SGBD), ou de modifier par configuration le DAO utilisé pour gérer la persistence des données de l'application.

3.1. Création du contexte

Créez une interface `CinemaContext`, de façon à ce que :

- le serveur puisse indiquer une référence à un objet `ProgrammationDAO` existant
- les différents composants puissent obtenir une référence sur ce DAO

En clair, il s'agit d'un objet qui stocke une référence sur un DAO et possède deux accesseurs sur ce champ.

Créez une classe d'implémentation `CinemaContextImpl` de cette interface, instanciez-la dans votre serveur et ajoutez-la en tant que composant de votre conteneur.

### 3.2. Modification de l'arbre de dépendances

Vous allez modifier les composants du conteneur ayant une dépendance sur un objet `ProgrammationDAO` pour qu'ils dépendent de `CinemaContext`.

Remarque : tant qu'à faire, servez-vous de la documentation de PicoContainer pour utiliser un autre type d'injection de dépendances que par constructeur.

- Modifiez les constructeurs de vos classes d'implémentation de Cinema (abstraites ou non), de façon à ce qu'ils prennent en paramètre un CinemaContext et non plus un ProgrammationDAO.
- Dans les composants, récupérez le DAO par des appels à la méthode correspondante (getDAO() ?) du contexte avant leur démarrage (méthode `start()`)
- L'instance du DAO sera récupérée par le serveur dans le conteneur à l'aide d'un `getComponent()`, et ajoutée au contexte à l'aide d'une méthode spécifique (`setDAO` ?).

### 3.3. Initialisation et utilisation du contexte

Enfin, mettez en place les méthodes correspondantes du contexte de façon à ce que les classes d'implémentation de Cinema puissent définir et récupérer une référence sur le DAO (`setDAO()`, `getDAO()` ?) .

Testez votre application. Vous pouvez ensuite par exemple vous servir du contexte pour filtrer les appels au DAO et ne renvoyer la bonne référence que si la méthode est appelée par une instance de type `CinemaRessourceXxx` (voir [ici](http://www.javalobby.org/java/forums/t67019.html) ou [là](http://stackoverflow.com/questions/421280/in-java-how-do-i-find-the-caller-of-a-method-using-stacktrace-or-reflection) pour des exemples de code sur comment trouver la classe appelant une méthode).

Remarque : dans ce cas, supprimez l'appel à la méthode `toString()` de l'instance du DAO dans l'affichage de la méthode `start()` des cinémas.

### 3.4 Généralisation du contexte

Actuellement, votre contexte n'est capable que de gérer un DAO. Modifiez-en l'API pour qu'il puisse stocker des références à tous les types d'objets et que ces objets soient accessibles par un nom (String).

Testez ce contexte générique en lui injectant aussi la liste des salles et en forçant le passage par le contexte pour permettre aux cinéma-ressources d'obtenir cette liste.

> Votre serveur a désormais une responsabilité supplémentaire : en plus de fournir un conteneur de composants métier, il gère un contexte pour l'isolation des composants du conteneur. Le contexte est accessible à l'intérieur du conteneur pour permettre et contrôler l'accès par les composants aux éléments externes tels que le DAO. Vous avez mis en place les principaux éléments d'un framework applicatif, que vous allez perfectionner dans la suite.

## 4. Création d'un Annuaire

Encapsulez le contexte dans une structure de type annuaire (cf. cours). Un annuaire sera une hiérarchie de contextes, spécifiques à différents éléments de votre conteneur et de vos applications. Ces éléments correspondront aux différents types d'objets à isoler (les cinéma-ressources, la liste de salles et les DAOs). Faites en sorte que votre annuaire soit capable de décomposer les noms de la manière suivante : nom de contexte + "/" + nom d'objet stocké.

Vous allez maintenant commencer à remplir votre annuaire. Bindez :

- dans le contexte racine de l'annuaire : le serveur
- dans un contexte spécifique à l'application : les objets "de premier niveau" (ici, les différents cinema-ressources), c'est-à-dire qui répondent aux requêtes du client,
- dans un sous-contexte du précédent : les objets métier spécifiques à l'application (ici, la liste)
- dans un sous-contexte du contexte de l'application, spécifiquement dédié à la persistence : les instances de ProgrammationDAO et de ReservationsDao

Remarque : les bindings dans l'annuaire s'effectuent avec des références à des instances déjà créées. Ces instances doivent donc être déjà créées (éventuellement par le conteneur), l'annuaire étant uniquement un moyen de permettre les accès à ces objets.

Modifiez vos tests fonctionnels pour qu'ils démarrent séparément l'annuaire et le serveur, que le serveur s'enregistre dans l'annuaire, mais que les tests ne disposent que d'une référence sur l'annuaire. Faites en sorte qu'au démarrage, le ils "découvrent" le serveur en passant par l'annuaire.

> Vous venez de construire quelque chose de similaire à un annuaire JNDI, qui pernet aux composants d'accéder à des références sur des objets interne au conteneur ou distants. L'avantage de cette méthode est qu'elle fonctionne quelles que soient les implémentations du conteneur et du composant, et qu'elle permet d'utiliser plusieurs implémentations différentes d'un objet pour une même interface.

### Aspects dynamiques de l'annuaire (bonus)

Actuellement, vos objets interrogent l'annuaire pour récupérer des références à d'autres objets. Cependant, il peut arriver qu'une référence sur un objet change, par exemple parce qu'un objet n'est plus disponible ou qu'une nouvelle version a été implémentée. Vous allez donc mettre en place un système à base d'événements qui permettra aux objets clients de s'abonner aux changements des références dans l'annuaire et donc de faire une nouvelle requête à l'annuaire à chaque notification.

Pour cela, modifiez l'implémentation de votre annuaire :

- mettez en place un pattern Observer
- faites en sorte que les objets puissent s'abonner aux événements "changement de référence sur le nom X" pour réagir en conséquence
- déclenchez cet événement à chaque rebind d'un objet sur un nom existant

> Cette stratégie de mise à jour dynamique d'une référence sur un objet est celle utilisée dans les frameworks à composants dynamiques, comme OSGi. Bien entendu, cette stratégie fonctionnera d'autant mieux si le client s'attend à trouver une implémentation d'une interface et non une instance d'une classe.

## 5. Mise en place d'un serveur d'applications

Dans cette partie, vous allez rendre votre serveur générique et permettre de lui faire exécuter différentes applications.

### 5.1 Configuration de l'application

Écrivez un fichier de configuration en XML (ou JSON) et stockez-y les dépendances de valeurs (type d'objet DAO, nom du fichier de stockage) et les types d'objets `CinemaRessourceXxx` correspondant à chaque commande (à la manière des fichiers web.xml utilisés dans un container de servlets). Ci-dessous un exemple de fichier de configuration :

```
<? xml version="1.0" ?>
<config>
  <application name="mon-cinema">
    <business>
      <component>
	    <class-name>monPackage.CinemaRessourceSalles</class-name>
      </component>
      <component>
	    <class-name>monPackage.CinemaRessourceFilms</class-name>
      </component>
      <component>
	    <class-name>monPackage.CinemaRessourceSeances</class-name>
      </component>
    </business>
    <service>
      <component>
	    <class-name>monAutrePackage.AnnuaireImpl</class-name>
      </component>
      <component>
	    <class-name>java.util.ArrayList</class-name>
      </component>
    </service>
    <persistence>
      <dao>
  	    <class-name>monTroisiemePackage.ProgrammationDAO</class-name>
  	    <param name="file">sample-data/mon-cinema.json</param>
      </dao>
      <dao>
  	    <class-name>monTroisiemePackage.ReservationsDAO</class-name>
  	    <param name="base">H2</param>
      </dao>
    </persistence>
  </application>
</config>
```
Remarque : dans le fichier de configuration, vous pouvez également indiquer :

- les noms des composants pour leur stockage dans l'annuaire
- les règles de contrôle d'accès à faire appliquer aux contextes dans lesquels ils se trouvent

Utilisez ces données dans la classe Serveur lors de l'instanciation des éléments des conteneurs et du contexte. Vous devrez utiliser l'API Reflection (et probablement `Class.forName()`) pour récupérer le .class défini par une chaîne de caractères.

### 5.2 Spécialisation des composants (bonus)

Éventuellement, vous pouvez spécialiser vos classes `CinemaRessourceXxx` et définir différents types de composants :

- Contrôleurs : ce seront les composants en front du serveur. Ils devront répondre à un type de requête du client, et organiser les traitements réalisés par les autres composants
- Vues : elles récupèreront les données fournies par les modèles et les formatteront pour qu'elles répondent aux attentes du client
- Modèles : ces composants réaliseront les traitements métier. Ils pourront faire appel à d'autres composants, de type entités
- Entités : ils représentent les données métier destinées à être liées à un support de persistance (`Film`, `SeanceDTO`)

Pour mettre en oeuvre cette spécialisation, vous pouvez soit faire hériter chaque type d'une interface spécifique, soit les annoter en fonction d'annotations définies en conséquence.

De la même manière, pour prendre en compte cette spécialisation au niveau du serveur, vous pouvez soit modifier ce serveur (et son mode de configuration) pour que le fichier de configuration mentionne la nature des composants, et que le serveur la "comprenne", soit rajouter un composant intermédiaire qui intercepte toutes les requêtes et s'appuie sur son propre mode de configuration pour instancier et rediriger les requêtes sur ces composants.

> À ce stade, vous avez réalisé un serveur d'applications, composé d'un serveur et d'un framework capable de mettre en place et de faire tourner différents types d'applications. Si vous avez réalisé la partie 5.2 en modifiant le serveur, vous avez créé un serveur qui fonctionne d'une manière proche des serveurs Java EE. Si vous l'avez réalisée par ajout d'une couche supplémentaire entre le serveur de la question 4 et l'application, votre serveur se rapproche plus d'un conteneur Spring inclus dans un conteneur de servlets.

## 6. Hiérarchie de conteneurs

Dans cette partie, vous allez changer l'implémentation de la liste de séances gérée par les cinéma-ressources en remplaçant le composant ArrayList du conteneur par une classe implémentant l'interface List mais dérivant la classe `DefaultPicoContainer`. Cette liste sera à la fois un composant et un conteneur fils du conteneur existant. Vos objets `Seance` deviendront des composants de ce conteneur fils : il va y injecter les dépendances (vers `ReservationDao`) et gérer leur cycle de vie. Vous devrez donc éventuellement modifier la classe `Seance` pour qu'elle soit compatible avec cette nouvelle implémentation.

### 6.1. Création d'une hiérarchie de conteneurs

Dans les classes `CinemaRessourceXxx`, remplacez l'ArrayList qui contient les objets Event par un nouveau conteneur, fils du premier (voir partie "Container hierarchies" de l'[introduction](http://picocontainer.com/introduction.html) sur le site picocontainer). Tant qu'à faire, le conteneur fils utilisera un autre type d'injection de dépendances que le premier : l'[injection par annotation de champs](http://picocontainer.com/annotated-field-injection.html). Vous pouvez utiliser deux méthodes pour créer le conteneur fils :

- [spécifier les factories dans le constructeur](http://picocontainer.com/annotated-field-injection.html)
  ```
  MutablePicoContainer fils = new DefaultPicoContainer(new Caching().wrap(new AnnotatedFieldInjection()), pere);
  ```
- [utiliser un builder](http://picocontainer.com/builder.html)
  ```
  PicoBuilder builder = new PicoBuilder(pere);
  MutablePicoContainer fils = (DefaultPicoContainer) builder.withAnnotatedFieldInjection().build();
  ```

Dans tous les cas, il faut construire le fils en lui passant une référence sur le père (fait dans les exemples ci-dessus) ET ajouter le fils comme composant du père : voir [ici](http://picocontainer.com/scopes.html) (à faire).

Remarque : si l'on veut que le conteneur père puisse résoudre les dépendances des `CinemaRessourceXxx` vers le conteneur fils, il faut ajouter ce dernier en tant que component et non en tant que child container dans le père. Contrairement à ce qui est marqué dans la doc, l'appel à la méthode `start()` du fils sera cascadée correctement.

En tant que composant, faites en sorte que `Seance` implémente l'interface Startable (vous vous servirez de la méthode `start()` plus tard). Pour cela, il faut que le conteneur fils possède également le comportement "Caching". Modifiez la création du fils en conséquence.

Dans `Seance`, supprimez le constructeur prenant les différents champs en paramètre et précédez la déclaration des champs de la classe `Seance` d'une annotation @Inject (de org.picocontainer.annotations).

### 6.2. Instanciation des composants

Dans cette question vous allez faire en sorte que votre conteneur fils reproduise le même comportement que celui de l'ArrayList utilisée dans les questions précédentes. Par conséquent, le serveur instanciera et gèrera dans ce deuxième conteneur autant de composants `Seance` qu'il y en a dans les cinema-ressources. En plus des modifications de la classe `Seance`, il faut modifier les méthodes de service des différents `CinemaRessourceXxx` en fonction des règles suivantes :

1. Comme tous les composants du conteneur fils sont du même type, il faut utiliser le nommage des composants et gérer un String qui indique leur numéro d'ordre pour les désigner. Pour simplifier cela, il est conseillé de dériver le type de conteneur que vous allez utiliser comme fils. Cela permettra de lui rajouter les comportements désirés (par exemple la gestion de l'indice des séances) tout en conservant celui du conteneur.
2. L'injection de dépendances étant faite au moment de l'appel des composants par le conteneur (`getComponent()`), il faut que le(s) composant(s) qui est/sont injecté(s) dans chaque instance de `Seance` représente(nt) les données à injecter. En d'autres termes, créez un composant `SeanceDto` dans le conteneur fils et faites en sorte qu'il possède des données à jour au moment où vous appellez la méthode `getComponent()` du conteneur fils.
3. Il faut arrêter le conteneur fils avant de supprimer un événement parmi ses composants. Veillez à bien utiliser les méthodes `stop()` puis `start()` du fils autour des traitements effectués par les cinéma-ressouces (si vous ne le "restartez" pas, il ne pourra pas faire l'injection des dépendances dans les séances).

Ensuite, il faut faire attention à des problèmes spécifiques pour chaque `CinemaRessourceXxx` :

- Ajout d'une séance : rien de particulier, sinon respecter les règles ci-dessus.
- Suppression d'une séance :

  Il faut modifier la méthode remove pour qu'elle supprime la référence aux séances effacées du conteneur fils (et qu'elle décale les suivantes si elles sont gérées avec un nom qui dépend de leur numéro d'ordre).

  ```
  private int removeSeance(SeanceDto dto) {
    //Remarque : on construit une Seance sans contexte, uniquement pour utiliser sa méthode equals()
    Seance p = new Seance(dto);
    int result = 0;

    List<java.lang.Object> seances = conteneurFils.getComponents();
    Seance temp;
    int i=0;

    conteneurFils.stop();
    try {
      for(Iterator<java.lang.Object> iter = eventslist.iterator(); iter.hasNext() ;) {
        temp = (Event) iter.next();
        if (p.equals(temp)) {
          temp.delete();
          conteneurFils.removeComponent("Event" + i);
          // On coupe le lien entre le conteneur et le composant
          // Plus aucune référence n'existe sur cet objet
          // On attend que le garbage collecting passe...
          conteneurFils.removeComponentByInstance(temp);
          result++;
        } else {
          // Il faut changer les noms de référence des composants suivant ceux qu'on a enlevés,
          // sans quoi on aura un problème pour en rajouter d'autres...
          if (result>0) {
    		conteneurFils.addComponent("Event" + (i-result), conteneurFils.getComponent("Event"+i));
            conteneurFils.removeComponent("Event"+i);
    	  }
    	}
        i++;
      }
    } catch (Exception e) {
      e.printStackTrace();
      result = -1;
    }
    conteneurFils.start();
    return result;
  }
  ```
- Récupération des séances : pas grand chose de nouveau à faire, si ce n'est modifier l'itérateur de la boucle pour qu'il itère sur les composants du conteneur.
