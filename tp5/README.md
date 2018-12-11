Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP OSGi

## Objectifs pédagogiques

Expérimenter une architecture à base de composants téléchargeables dynamiquement.

## Outils

Dans ce TP, vous utiliserez le framework [Felix](http://felix.apache.org/documentation/subprojects/apache-felix-ipojo.html), qui contient une implémentation minimale d'OSGi et une console ("gogo" shell) en ligne de commande. Un début de documentation de ce shell est disponible [ici](https://felix.apache.org/documentation/subprojects/apache-felix-framework/apache-felix-framework-usage-documentation.html#framework-shell).

## Découverte du framework Felix

Téléchargez et décompressez le framework. Vous pouvez ouvrir un shell en exécutant, depuis le répertoire principal du framework, la commande `java -jar bin/felix.jar`.

Utiliser la documentation située [ici](https://felix.apache.org/documentation/subprojects/apache-felix-framework/apache-felix-framework-usage-documentation.html#framework-shell) pour démarrer. Par exemple, taper `lb` (list bundles) pour avoir la liste des bundles installés par défaut.

### Chargement et exécution d'un bundle

Explorer le [Repository Felix OBR](http://felix.apache.org/downloads.cgi). Choisir et télécharger un ou plusieurs bundle(s), installer, lancer et stopper ce(s) bundle(s) comme indiqué [là](http://felix.apache.org/site/apache-felix-framework-usage-documentation.html#ApacheFelixFrameworkUsageDocumentation-installingbundles). Normalement, vous devez voir s'exécuter les méthodes de gestion du cycle de vie `start()` et `stop()`, d'implémentation de l'interface `BundleActivator`.

Dans la suite, vous pourrez avoir besoin de recourir à ce repo pour récupérer et déployer des bundles nécessaires à l'exécution de votre application.

### Réalisation d'un bundle avec iPOJO

Vous allez maintenant réaliser vous-même un bundle OSGi, en suivant le tutoriel situé [ici](http://felix.apache.org/documentation/subprojects/apache-felix-ipojo/apache-felix-ipojo-gettingstarted/ipojo-hello-word-maven-based-tutorial.html). Une fois les fichiers recopiés et les jars faits, suivre la même procédure que précédemment pour les déployer et les démarrer. Vous devrez démarrer les bundles suivants (dans "annotations/hello.felix.annotations/target/bundle") : `org.apache.felix.ipojo-1.12.1`, `hello.service-1.12.1`, `hello.impl.annotations-1.12.1` et `hello.client.annotations-1.12.1`.

## Reprise de l'application cinema

De la même manière que dans les TP précédents, vous allez reprendre votre application de cinema après la partie "uniformisation" du TP2. Vous allez "sortir" les cinemas du serveur et les placer dans des bundles séparés. Vous n'avez donc plus besoin d'un conteneur dans le serveur. Créer les bundles suivants :

  - un bundle exposant l'interface d'un `CinemaResource`
  - des bundles implémentant cette interface et contenant chacune de ces classes
  - un bundle qui gère les DAOs (dépendance des cinemas)
  - un client sous forme de servlet (vous aurez donc besoin d'un container de servlets)

Packager sous forme de Web Application Bundle (WAB) - Aide : http://coding.alasdair.info/2011/01/creating-web-application-bundle-using.html et http://www.javabeat.net/writing-an-osgi-web-application/ - et déployer dans OSGi. Tester.

**Indication :**  issue de la page d'accueil d'OSGi, à propos du fait que vos cinemas implémentent la même interface.

    What happens when multiple bundles register objects under the same interface or class? How can these be distinguished? The answer is properties. Each service registration has a set of standard and custom properties. A expressive filter language is available to select only the services in which you are interested. Properties can be used to find the proper service or can play other roles at the application level.

Source: http://www.osgi.org/Technology/WhatIsOSGi

### Gestion des versions

Réaliser un autre composant qui implémente l'interface d'un cinema, et qui renvoie une réponse de type "service non disponible". Donner à ce composant un numéro de version inférieur à celui des cinemas "fonctionnels". Modifier le fonctionnement de votre serveur pour qu'il cherche le cinema correspondant à une commande donnée et qu'il switche sur cette implémentation par défaut quand il ne trouve pas cette commande dans les cinemas disponibles (par exemple, avec un OR dans le filtre que la propriété indiquant la commande).

Simuler une panne en déployant et supprimant un service fonctionnel, sans arrêter le framework, et l'interroger entre temps avec le client. Vérifier que le framework vous renvoie bien la dernière version du service demandé.

### Chargement / déchargement à chaud

Modifier l'interface Web pour qu'elle permette d'envoyer au serveur toutes sortes de commandes et de paramètres correspondant à son interface. En clair, rajouter un champ texte qui permettra de taper une commande.
Côté serveur, récupérer cette commande et la passer en paramètre de l'appel de service. Suite au mécanisme de fallback de la question précédente, vous devez obtenir le message d'erreur générique.

Déployer un service fonctionnel correspondant à cette commande, sans arrêter le framework. Ré-interroger ce service avec le client et vérifier son fonctionnement.
Vous devez maintenant pouvoir déployer n'importe quel composant et l'interroger avec votre client Web.