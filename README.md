# E-Coffee-Android

Le projet E-COFFEE est développé par 4 étudiants de licence professionnelle Systéme numérique et développement d'objets connectés ([SNDOC](https://univ-avignon.fr/licence-professionnelle-systemes-numeriques-et-donnees-des-objets-connectes-sndoc--23377.kjsp)).

Le but de cette application est d'être couplée à une partie électronique d'une machine à café et de créer un "_reverse engineering_" : c'est à dire de rendre entièrement connectée à Internet cette machine à café classique. Il sera possible de choisir et préparer son type de café favori depuis son _smartphone_ et aussi de connaitre les differents types de café que la marque peut proposer et de voir sa consommation de caféine en temps réel par exemple.


## Détails Techniques

Pour la partie Developpement mobile, le choix s'est porté pour la partie _front-end_ sur une programmation native c'est à dire en Swift pour IOS, JAVA (et/ou Kotlin) pour Android. Pour la partie _back-end_ (serveur et base de données), Firebase a été retenu.

## (Optionnel)

Possibilité de création d'un café avec les assistants vocaux du marché (Google Home, Alexa Amazon, etc...)

## Hardware

Pour la partie _hardware_, l'utilisation d'un microcontrôleur de type ESP32 développé par la société Espressif est bon compromis car il intègre un module WiFi et également un module Bluetooth 4.

## Matériel nécessaire

* Machine à café Delonghi Dinamica
* ESP32-Wroom32
* Capteur quantité eau
* Capteur quantité marc
* Capteur présence de tasse 

## Fonctionnalités

* connexion au réseau Wifi du client puis au serveur Firebase
* création d'un compte client et authentification (Firebase)
* récuperation des données essentielles à la création d'un café (quantité, type de café).
* émission des données pour l'utilisateur (quantité eau, auantité de marc, qualité de l'eau). 


© Enzo Toyos 2021
