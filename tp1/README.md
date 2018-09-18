# TP1: Révisions Java

Ce TP a pour objectif de réviser la programmation Java, ainsi que de prendre en main l'environnement de développement utilisé à l'université (forge, intégration continue).
Il n'est pas à rendre, mais il vous permettra de faire les autres TP dans de meilleures conditions.
Les questions demandant une réalisation technique (développement Java/Web) sont assez indépendantes les unes des autres.
Vous pouvez par exemple réaliser le travail sur l'interface Web sans implémenter tous les DAOs. 

## Découverte du projet "réservation de places de cinéma"

Si ce n'est pas déjà fait:
> * Lire la [description du métier](../metier.md) qui servira à illustrer les différents TP de cette année.
> * Lire les [consignes sur les TPs](../README.md).

Le module `metier-base` contient un ensemble de classes décrivant le métier de l'application.

> Lire chaque classe de ces modules et faire le lien entre les différentes méthodes et le métier, comprendre le fonctionnement du code.

## Maven

Avant de se lancer dans la partie développement du TP, il est important de comprendre comment il va se construire.
C'est [Maven](https://maven.apache.org/) qui sera majoritairement utilisé dans les TPs.
Chaque module contient un fichier de configuration `pom.xml`.

> Lire les fichier `pom.xml` des modules et de la racine du dépôt.

On peut remarquer que les fichiers de `metier-base` et de `tp1` sont presque vides. 
Seules les dépendances sont décrites dans ces fichiers.
L'essentiel de la configuration se trouve dans le fichier à la racine, ce qui permet de partager et de maintenir en cohérence un ensemble de paramètres de configuration (versions de différentes bibliothèques utilisées, configuration des plugins, etc).
Le système d'héritage de Maven va en effet permettre aux sous-modules de récupérer les informations de depuis le fichier situé à la racine.


> Depuis un terminal, lancer la phase d'installation via `mvn install`. 
> Regarder la sortie et essayer de comprendre les différentes actions effectuées par cette commande. 
> Essayer la commande avec les phases suivantes pour comprendre les leurs dépendances:
> * compile
> * test-compile
> * test
> * package
> * install

Suivre ensuite les [instructions](http://maven.apache.org/ide.html) pour lancer la phase d'installation (et l'analyse de code) depuis votre IDE.

## Qualité de code avec Checkstyle

Lancer `mvn site` dans le module `metier-base`. 
Ouvrir le fichier `target/site/index.html` puis trouver dans le menu la section _Project Reports_ -> _Checkstyle_ et vérifier les erreurs.

> Configurer une utilisation supplémentaire du plugin checkstyle de façon à vérifier et faire échouer le build si le style n'est pas respecté.

## Intégration continue

> Mettre en place un fichier `.gitlab-ci.yml` à la racine de votre dépôt.

Le fichier [.gitlab-ci.yml](../exemples/gitlab-ci.yml) peut servir de point de départ, mais il faudra probablement l'adapter (_e.g._ changer [le tag de l'image `maven`](https://hub.docker.com/_/maven/), les étapes d'intégration continue, en ajouter ou en retirer, etc).

À chaque push sur la forge, l'intégration continue est lancée automatiquement (_c.f._ Le menu CI/CD de votre projet sur l'interface Web de la forge.

> Faire une étape de vérification séparée pour checkstyle dans le cadre de l'intégration continue.

L'intégration continue va échouer à cause du test unitaire `TestAllSeances::testSeances`.
Cela fait l'objet de la section suivante.


## DAO JSON

> Dans le module `metier-base`, créer un ensemble de DAOs fonctionnant sur la base de fichiers JSON
> * On pourra utiliser par exemple la bibliothèque [Jackson](https://github.com/FasterXML/jackson)
> * Par défaut, les données seront sauvegardées dans un fichier `sample-data/mon-cinema.json`.
> * Élaborer un [schéma JSON](http://json-schema.org/) et  vérifier que vos désérialisations sont conformes à ce schéma via des tests unitaires. Ce schéma devra être compatible avec `sample-data/mon-cinema.json`.
> * Tester et assurer la qualité du code. Le test unitaire `TestAllSeances::testSeances` devra maintenant fonctionner.

Votre intégration continue doit maintenant s'exécuter sans erreurs. Corriger au besoin.

### Remarques et liens

* La classe `org.everit.json.schema.Schema` de la bibliothèque [JSON-Schema](https://github.com/everit-org/json-schema) permet de tester le type des documents JSON.
* Le site https://search.maven.org/ peut être utilisé pour chercher des modules maven et obtenir la configuration XML correspondante.


## DAO en mémoire

Abstraire vos DAO JSON en interfaces dans le module `metier_base`.
Dans le module `tp1`, créer des implémentations de vos interfaces de DAO fonctionnant en conservant simplement les objets en mémoire.
Écrire des tests unitaires pour ces DAO.

Vérifier que l'intégration continue est toujours "au vert", corriger les problèmes au besoin.

## DAO relationnel

> Dans le module `tp1`, utiliser [JPA](https://fr.wikipedia.org/wiki/Java_Persistence_API) pour créer des DAOs qui s'interfaceront avec une base de données relationnelle:
> * Tester votre mapping objet-relationnel en écrivant un/des test-s unitaire-s dédié-s avec une base de donnée H2 résidant en mémoire. 
> * On pourra utiliser Hibernate ou EclipseLink comme runtime JPA.
> * Assurer la qualité du code.

### Remarques / liens utiles:
* [Cours de M1 sur les ORM](http://liris.cnrs.fr/ecoquery/dokuwiki/lib/exe/fetch.php?media=enseignement:bdav:mapping-objets-relationnel-xml.pdf)
* [Tutoriel JPA](https://docs.oracle.com/javaee/6/tutorial/doc/bnbpz.html)
* [H2](http://h2database.com/html/main.html) et [configuration pour une BD en mémoire](http://h2database.com/html/features.html#in_memory_databases)
* Un exemple de fichier [`persistence.xml`](../exemples/persistence.xml) se trouve dans le répertoire `exemples` à la racine.
* Comme pour le cas JSON, ces DAO vont nécessiter l'utilisation de bibliothèques externes. Il faut bien penser à déclarer les versions dans le `pom.xml` racine et à les utiliser dans celui du `tp1`.
* Il est possible d'annoter les classes du module `metier-base` pour spécifier les mappings.
* Ne pas utiliser Spring Data afin d'éviter les dépendances vers Spring.

