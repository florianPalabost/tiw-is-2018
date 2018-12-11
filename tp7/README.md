# TP Implémentation de services SOAP

L'objectif de ce TP est de comprendre les principes d'implémentation de Web services illustrés à travers la plateforme Java en utilisant le _framework_ [SpringBoot](https://projects.spring.io/spring-boot/) ainsi que le framework [CXF](http://cxf.apache.org/).

Il est demandé d'exposer les fonctionnalités de réservation et d'annulation de réservation sous forme de service Web de type SOAP.

Pour cela, reprendre la version *SpringBoot* de votre projet et faire les éventuelles modifications nécessaires pour qu'elle fonctionne avec SpringBoot `2.0.7.RELEASE`.
Adapter ensuite la configuration dans le `pom.xml` afin d'intégrer Apache CXF, comme pour l'application `banque`.

Dans ce TP, on exposera de différentes manières les fonctionnalités de réservation sous forme de service SOAP.
Le fichier [reservation.wsdl](src/main/resources/reservation.wsdl) contient les définitions du service correspondant.


## Implémentation via un générateur de code


### Génération

Utiliser le plugin cxf-codegen-plugin ([doc](http://cxf.apache.org/docs/maven-cxf-codegen-plugin-wsdl-to-java.html)) pour générer une interface Java représentant le service à implémenter.
 
>  Le code généré ne devra pas être versionné et se trouvera (par défaut) dans l'arborescence du répertoire target. 
>  Il est d'ailleurs conseillé de ne pas configurer de sourceRoot. 

Parcourir le code généré et faire le lien avec le contenu du fichier [reservation.wsdl](src/main/resources/reservation.wsdl).

### Intermède: composant métier

Créer un composant Spring pour implémenter le métier des réservations (à priori, juste un appel bien senti à un *repository* complété d'un appel à une méthode métier).

### Implémentation via @WebService

Créer un composant qui hérite de l'interface générée pour le service. Ce composant sera annoté avec `@WebService` (bien choisir les paramètres de l'annotation: au moins l'interface du service).
Injecter le composant précédent (celui du métier des réservations) et faire les bon appels dans le corps des méthodes correspondant aux opérations du service.

Configurer le déploiement du service via CXF (*c.f.* l'application `banque`).

Lancer votre serveur SpringBoot et tester avec SoapUI.

## Intercepteurs

### Intercepteur du header

Créer un intercepteur SOAP (*c.f.* [CM Intercepteurs](https://perso.liris.cnrs.fr/ecoquery/enseignement/tiw1-is/tiw5-handlers.pdf)) qui extrait la valeur d'un élément `api-key` du header et l'injecte dans le contexte du message.

### Intercepteur audit

Créer un intercepteur logique (*c.f.* [CM Intercepteurs](https://perso.liris.cnrs.fr/ecoquery/enseignement/tiw1-is/tiw5-handlers.pdf)) qui utilise la valeur précédemment injectée et affiche une ligne de log `"Accès via la clé d'API xyz"`.

### Activation des intercepteurs

Configurer une chaîne d'intercepteurs JAX-WS sur le service précédent.

Tester avec SoapUI pour vérifier que le log s'affiche correctement.

**Pour aller plus loin:** créer une entité User avec le *repository* SpringData associé. 
Cette entité contiendra en particulier un champ `email` et un champ `apikey`.
Créer un intercepteur logique qui récupère la clé d'API et injecte dans le contexte l'utilisateur.
Modifier le handler d'audit pour afficher l'email au lieu de la clé. 

Tester avec SoapUI pour vérifier que le log s'affiche correctement.

## Contrôleur de service


Le rôle de ce contrôleur est double: extraire le contenu XML dans des instances de classes Java et aiguiller la suite du traitement vers la bonne méthode du composant d'implémentation du service.

Créer un nouveau composant Spring implémentant l'interface [Provider<Source>](https://docs.oracle.com/javaee/7/api/index.html?javax/xml/ws/Provider.html). 
La méthode invoke devra convertir le XML du payload en objets Java et appeler la bonne méthode du composant d'implémentation du service que vous avez mis en place à l'étape précédente.

Déployer ce composant comme un Web service à une autre adresse et tester avec SOAPUI.


## Contrôleur principal (front)

 Implémenter un contrôleur point d'accès au service via un @Controller SpringMVC. 
 Ce contrôleur traitera les requêtes HTTP et extraira le contenu (payload) du message via l'API SAAJ. 
 Il redirigera ensuite le traitement vers le composant Web service provider de l'étape précédente.

Utiliser soapUI pour tester votre contrôleur point d'accès. 

> Pour fabriquer correctement les MimeHeaders, il faut les copier via une boucle depuis les headers de la requête HTTP. 

> En cas d'utilisation de l'API XML de transformation pour faire des copies entre représentations XML, penser à SAAJResult qui permet d'écrire dans noeud XML d'un message SOAP.

