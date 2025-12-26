# Simulation de Gare Multithreadée et API REST

Ce projet simule le fonctionnement d'une gare ferroviaire en gérant les problématiques de synchronisation entre threads (trains, voyageurs) et propose une interface de contrôle via une API REST.

## 📋 Présentation
Le projet est divisé en deux parties majeures :
1. **Simulation multithreadée** : Modélisation des comportements de la gare, des trains et des clients.
2. **API Rest de contrôle** : Supervision et manipulation de la simulation via la librairie Restlet.

## 🚀 Fonctionnalités de la Simulation

### Gestion de la Gare
* **EspaceQuai** : Composé d'un nombre fixe de voies, chaque voie accueillant un seul train à la fois.
* **EspaceVente** : Permet l'achat de billets (en nombre limité) avec un temps d'impression requis.

### Entités (Threads)
* **Trains** : 
    * Arrivent à une vitesse comprise entre 50 et 300 km/h.
    * Stationnent durant un temps `ARRET_TRAIN` pour l'embarquement.
    * Possèdent une capacité de places libres aléatoire à l'arrivée.
* **Voyageurs** : 
    * Doivent acheter un billet avant de chercher un train.
    * Parcourent les trains à quai pour trouver une place ou attendent avant de réessayer.

## 🌐 API REST (Partie 2)
L'API permet de suivre et modifier l'état de la simulation:
* **Trains (`/trains`)** : Lister les trains avec leur état (En route, En attente, En gare, Parti) via `GET` ou en ajouter via `POST`.
* **Voyageurs (`/voyageurs`)** : Lister les voyageurs avec leur état (En route, Muni d'un ticket, Monté) via `GET` ou en ajouter via `POST`.

## 🛠️ Installation et Exécution
1. S'assurer d'avoir le JDK Java installé.
2. Inclure la librairie **Restlet** dans le classpath pour la Partie 2.
3. Compiler et lancer la classe principale `Gare`.
