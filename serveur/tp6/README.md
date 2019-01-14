Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP Clients SOAP

## Objectifs pédagogiques

Utiliser un logiciel de test de services Web et utiliser une API bas niveau pour accéder à un service.

## Outils

Ce TP s'appuiera sur le logiciel [SoapUI](https://www.soapui.org/).
Miroirs locaux, version OpenSource 5.2.1: [Linux](https://perso.liris.cnrs.fr/ecoquery/files/SoapUI-5.2.1-linux-bin.tar.gz) [MacOSX](https://perso.liris.cnrs.fr/ecoquery/files/SoapUI-5.2.1-mac-bin.zip) [Windows](https://perso.liris.cnrs.fr/ecoquery/files/SoapUI-5.2.1-win32-standalone-bin.zip).

SoapUI permet de faire des requêtes vers des services SOAP et REST et de vérifier les réponses produites.
Il est également possible d'enchaîner les requêtes pour construire des scénarios complexes.

## Service SOAP banque

Le sous répertoire `banque` du dépôt contient un Web service rudimentaire permettant de réaliser des opérations bancaires.
Attention, ce service est à compiler et à exécuter avec Java 8, il n'est pas compatible en l'état avec Java 11.

Remarques: 

- la base de donnée interne à ce service réside uniquement en mémoire et est réinitialisée à chaque redémarrage du service.
- le compte n°0 contient une grosse somme de départ afin de pouvoir facilement alimenter les autres comptes.

## WSDL généré par CXF

Lancer le service de banque (via `java -jar chemin/vers/tiw1-is-2018/banque/target/banque-2018.jar`) et vérifier la liste des services à l'adresse [http://localhost:8080/services](http://localhost:8080/services).
Suivre le lien vers le WSDL du service pour accéder à ce dernier (attention, c'est du XML, donc il peut être nécessaire d'afficher le source du document pour le visualiser).

Comprendre la structure du WSDL et le lien avec la structure de la classe `fr.univlyon1.tiw.tiw1.banque.service.CompteService`.

## Requêtes simples avec SoapUI

Lancer SoapUI et créer un nouveau projet SOAP. 
Indiquer l'URL du WSDL dans le champ *Initial WSDL*.
Laissez cochée la case *Generate sample requests for all operations*.

Naviguer vers *infosCompte* -> *Request 1* et changer la valeur de arg0 pour indiquer un numéro de compte (*c.f.* [data.sql](../banque/src/main/resources/data/data.sql)).
Exécuter la requête.

Cloner la requête et changer la valeur pour un numéro de compte inexistant et exécuter à nouveau.

## Suites de tests

### Tests de requêtes simples

Créer une nouvelle suite de tests dans le projet SoapUI.
Créer un nouveau Test Case, contenant un Test Step qui fait une requête vers l'opération `infoCompte` du service banque (similaire à la première requête ci-dessus). 
Ajouter une assertion *Not SOAP Fault*.
Lancer le test case (pour essayer), puis la suite de tests complète.

Créer un deuxième Test Case pour vérifier que le service renvoie bien une erreur si un compte n'existe pas.

Reprendre le premier Test Case et ajouter une vérification sur la valeur du solde du compte.
(HINT: expression XPath: `//solde`).

### Enchaînement de tests steps

Il est possible d'enchaîner plusieurs "steps" dans un "case".
Créer un test case qui créée un nouveau compte, autorise ce compte à effectuer un prélèvement sur le compte 0 puis effectue un prélèvement du nouveau compte sur le compte 0.

Remarque:

- le numéro du nouveau compte n'est pas connu à l'avance
- il est possible d'injecter une valeur depuis un message vers un autre à l'aide d'un "step" *Property Transfert*.

### Tests plus poussés

Reprendre la suite de test de la classe `fr.univlyon1.tiw.tiw1.banque.service.CompteServiceTest` et porter (i.e.  réimplémenter) ces tests dans SoapUI.

## Génération de client SOAP

Dans le projet `tp6` (celui de ce README), ajouter des tests JUnit qui reprennent la fonctionnalité des tests de  `fr.univlyon1.tiw.tiw1.banque.service.CompteServiceTest`, mais avec un client basé sur la *JAX-WS Dispatch APIs*.

Ajouter la configuration Maven pour obtenir un client généré via le *WSDL2Java generated Client*, puis créer un autre test JUnit similaire au précédent. 
Ne pas hésiter à enregister le WSDL dans un fichier pour travailler dessus.

Références: 
- http://cxf.apache.org/docs/how-do-i-develop-a-client.html
- http://cxf.apache.org/docs/using-cxf-with-maven.html
- http://cxf.apache.org/docs/maven-cxf-codegen-plugin-wsdl-to-java.html

