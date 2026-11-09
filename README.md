# Calculatrice

## Présentation 

** Le projet consiste à développer une calculatrice en Java en utilisant la notation polonaise inverse (NPI).

Contrairement à une calculatrice classique, la NPI repose sur l'utilisation d'une pile dans laquelle les nombres sont enregistrés avant l'exécution des opérations.

L'interface graphique a été développée avec Java Swing.

## Fonctionnalités

Les fonctionnalités intégrées sont la saisie des nombres entiers et décimaux, les opérations basiques (addition, soustraction, multiplication, division), pop (retirer un nombre de la pile), swap (échanger les deux éléments au sommet de la pile), clear ou C (effacer la valeur courante et libérer la pile), push (ajouter un nombre à la pile) +/- (changer le signe d'un nombre), Backspace ou < (supprimer le dernier caractère saisi), affichage des 10 derniers nombre de la pile, agrandissement automatique de la capacité de la pile.


## Adaptation du cachier des charges

Une modification a été apportée au comportement de la fonction pop() par rapport au cahier des charges initial.

Dans notre implémentation, cette fonction agit comme une opération drop(): elle supprime le dernier élément enregistré dans la pile sans le transférer dans l'accumulateur.

Ce choix a été effectué pour des raisons d'ergonomie et de simplicité d'utilisation.

## Technologies utilisées 
Java 17, Java Swing pour l'interface graphique, Visual Studio Code, Git et GitHub.


## Justification des choix techniques

Nous avons choisi d'utiliser Swing plutôt que JavaFX en raison de sa simplicité de mise en place et de son intégration directe à Java, sans configuration supplémentaire de bibliothèque graphique. 

Concernant l'environnement de développement, nous avons privilégié Visual Studio Code, avec lequel nous étions déjà familiarisés. 

Nous avions initialement envisagé d'utiliser Eclipse, mais son adaptation à notre environnement de travail s'est révélée moins pratique. Nous avons donc conservé VS Code afin de faciliter le développement et la collaboration

## Architecture du projet

Le projet suit une organisation similaire au modèle MVC (Modèle-Vue-Contrôleur) .

- Pile.java pour la gestion des nombres dans la pile,
- Calculatrice.java: opérations et logique de calcul,
- VueCalculette.java: interface graphique,
- Controleur.java: gestion des interactions entre l'utilisateur, la vue et le modèle,
- Main.java: point d'entrée de l'application

