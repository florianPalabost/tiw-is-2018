Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP Spring framework

## Objectifs pédagogiques

- (Re)découvrir le framework Spring
- Prendre en main / approfondir quelques uns de ses différents sous-projets

## Préambule

Dans ce TP, vous allez utiliser le framework Spring et certains des principaux [projets de l'infrastructure Spring](http://spring.io/projects). Dans un premier temps, vous travaillerez à partir d'une version fonctionnelle et orientée-composants de votre application cinéma, c'est-à-dire comprenant plusieurs `CinemaRessourceXxx` (conformes à la même interface), un annuaire, etc.

## Spring Boot

Créez un projet Forge et poussez-y la structure d'un projet Maven et Spring préconfiguré pour les sous-projets Spring demandés dans les questions suivantes. Pour obtenir ce projet, vous pouvez le créer vous-mêmes ou l'obtenir avec l'[outil de configuration automatique](https://start.spring.io/) fourni par Spring Boot.

## Spring Framework

Dans cette section, vous allez utiliser les différents modules de [Spring Framework](http://projects.spring.io/spring-framework/), dont vous trouverez une documentation [ici](http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/overview.html#overview-modules).

### Spring Core Container

Faites les manipulations suivantes pour que votre code puisse être utilisé par [Spring Core Container](http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/overview.html#overview-core-container) :

- Transformez vos `CinemaRessourceXxx`, `Salle`, `Film` et `Seance` en beans Spring pour permettre au conteneur de Spring DI de les gérer dans l’application
- Remplacez l’annuaire JNDI par un Spring Context

&Agrave; ce stade, vous devez avoir une application standalone fonctionnelle, que vous pouvez tester de la même façon que lors du TP précédent.

### Spring (Web) MVC

Refactorez votre code en MVC pour qu'il fonctionne selon l'architecture [Spring Web MVC](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#spring-web).

Vous exposerez votre application en deux parties :

1. Un "back-office" qui sera utilisé par les administrateurs du cinéma pour mettre à jour les films et les séances. Cette partie doit permettre de réaliser les opérations CRUD classiques sur ces 2 classes. Elle exposera des fomulaires et génèrera les pages Web côté serveur avec un ViewResolver vous permettant de "templater" simplement les données (JSP ou [Thymeleaf](https://www.thymeleaf.org/)).
2. Une interface REST destinée au front qui sera utilisée par les clients du cinéma. Elle permettra  :

- d'exposer en lecture uniquement les films et les séances,
- de réaliser les 4 opérations sur les réservations pour une séance. Ne vous préoccupez pas des problèmes d'authentification pour l'instant.

Note : pour pouvoir utiliser des JSP pour faire le templating, vous pouvez vous aider :

- du projet `exemple-spring-boot-avec-jsp` dans ce répertoire (interrogez http://localhost:8080/hello?name=Toto)
- de [ce tutoriel](https://howtodoinjava.com/spring-boot/spring-boot-jsp-view-example/).

### Spring Test

Utilisez [Spring Test](http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/overview.html#overview-testing) pour écrire des tests :

1. Unitaires pour chacun des composants ; utilisez éventuellement 1 ou plusieurs mocks ad hoc
2. D’intégration (1 minimum)

### Spring Data

À l’aide de [Spring Data](http://projects.spring.io/spring-data), créez un DAO s’appuyant sur JPA pour implémenter la persistance des réservations.

- Toutes les opérations d’écriture réalisées sur les réservations seront encapsulées dans des transactions. Pour la gestion des transactions, vous utiliserez une approche déclarative
- Utilisez des transactions pour gérer les séries d'inscriptions aux séances

### Spring Security

À l’aide de [Spring Security](http://projects.spring.io/spring-security), utilisez l’authentification via OAuth pour faire en sorte que chaque client qui souhaite réserver pour une séance s'authentifie.

### Spring AOP

Utilisez [Spring AOP](http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/overview.html#overview-aop-instrumentation) pour créer un nouvel aspect chargé d'intercepter la création de séances et de tenir à jour un "tableau de bord", accessible aux gestionnaires du cinéma dans le back-office.
