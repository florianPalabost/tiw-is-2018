# TIW1 - Intergiciels et Services, année 2018-2019

## Cours

### Liens vers les CMs

  * [CM1 : Révisions Java](https://perso.liris.cnrs.fr/ecoquery/enseignement/tiw1-is/01-intro-java.pdf)
  * [CM2 : Inversion de Contrôle et conteneurs](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM_IS_conteneurs.pdf)
  * [CM3 : Contexte et Annuaire](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM_IS_contexte.pdf)
  * [CM4 : Intercepteurs](https://perso.liris.cnrs.fr/ecoquery/enseignement/tiw1-is/tiw5-handlers.pdf)
  * [CM5 : Architectures Orientées Composants](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM_IS_composants.pdf) et [Spring](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM_IS_spring.pdf)
  * [CM6 : TypeScript](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/revealJS/#TypeScript) et [Angular](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/revealJS/#Angular)
  * [CM7 : Frameworks de composants dynamiques](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM_IS_OSGi.pdf)
  * [CM8 : Objets Distribués](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM_IS_objets_distribues.pdf) et [Services Web](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM_IS_services_web.pdf)
  * [CM9 : Composition de services et communication orientée-messages](https://perso.liris.cnrs.fr/ecoquery/enseignement/tiw1-is/CompositionMessaging.pdf)
  * [CM10 : Microservices](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM_IS_microservices.pdf)
  * [CM11 : Orchestration de conteneurs]() - À venir
  * [CM12 : Cache](https://perso.liris.cnrs.fr/ecoquery/enseignement/tiw1-is/CacheApplicatif.pdf)
  * [CM13 : Web performance]()  - À venir
  
### Interventions extérieures

  * [Architectures des SI]() (CGI)
  * [Performance des SI]() (CGI)

## TPs

Ce dépôt constitue la base pour les TPs de l'UE.

### Présentation

Les TPs s'appuient essentiellement sur une application de réservation de places de cinéma.
Le métier codé dans cette application est décrit dans le fichier [metier](metier.md).

Le présent projet constitue la base qui servira de point de départ pour tous les TPs de cette UE en 2018-2019.

Le module `metier-base` comprend un ensemble de d'interfaces et de classes Java qui spécifient et implémentent une partie de l'application utilisée pour illustrer les différents concept et technlogies vus dans l'UE.

### Liste des TPs

* [TP1: révision Java](tp1/README.md)
* [TP2: containers](tp2/README.md)
* [TP3: Spring](tp3/README.md)
* [TP4: Angular](tp4/README.md)
* [TP5: OSGi](tp5/README.md)
* [TP6: Clients SOAP](tp6/README.md)
* [TP7: Service SOAP](tp7/README.md)
* [TP8: RabbitMQ](tp8/README.md)

Cette liste sera complétée au fur et à mesure de l'avancement dans l'UE.

Lorsqu'ils sont disponibles, il est conseillé de lire les sujets de TP avant la première séance, même si certaines parties resteront obscures avant le premier cours.
Si vous maîtrisez déjà certaines notions (de par votre expérience personnelle), il vous est possible de commencer le TP en avance.

### Modalités de rendu

Les TPs sont à réaliser en binôme (ou éventuellement seul.e).
Les TPs qui seront évalués sont à rendre via [la forge de l'université](https://forge.univ-lyon1.fr).


Tous les rendus devront suivre les consignes suivantes:

* Un rendu est consitué d'un projet GitLab sur la forge. Dans le cadre de cette UE, le projet est identifié sur GitLab par _**son URL de clone HTTPS**_.
* Pour rendre un projet, il faut indiquer l'URL de clone HTTPS dans case appropriée sur [tomuss](https://tomuss.univ-lyon1.fr).
* Les _**deux**_ membres du binôme doivent indiquer l'URL de clone HTTPS dans tomuss: lors de la correction, les binômes sont reconsitués à partir de cette information.
* Afin qu'un projet puisse être évalué, il faut ajouter vos enseignants (`emmanuel.coquery` _**et**_ `lionel.medini`) comme membres du projet en leur donnant le rôle de _Reporter_.
* Les TPs seront clonés peu après la date de rendu. 
  La révision utilisée sera la dernière révision avant la date de rendu de la branche `TPX`, où `X` est le numéro de TP.

> Le non respect de ces consignes de rendu entraînera systématiquement une pénalité dans la note du TP
