# ▶ Lancement

- Run la configuration "app" pour démarrer l'application.
- Lancer le Device Manager.

Il arrive parfois que l'émulateur android ne parviennent pas à trouver `https://stations-npev.cleverapps.io`
Cela peut arriver s'il ne s'est pas connecté au réseaux internet
Pour résoudre ce problème :
        - Quitter la fenêtre de l'émulateur 
        - Relancer la configuration "app" 
        - Lancer le Device Manager

Il se peut que votre ordinateur ne crée pas lui même le fichier `local.properties`
        - créer un fichier `local.properties` dans le dossier `Gradle Scripts`
        - écrire la ligne `sdk.dir=C\:\\Users\\[votre nom d'utilisateur]\\AppData\\Local\\Android\\Sdk` dans ce fichier `local.properties`

# ✍ Description

Voici une application vous permettant de suivre la disponibilité des carburants en Île de France.


## 📝 Fonctionnalités
Pour cela, vous avez accès à plusieurs fonctionnalités :
- L'accès à diverses données sur les carburants (nom, prix, disponibilité, etc.)
- La localisation des stations
- La mise en favori
- Une barre de recherche
- La mise à jour des données

## 📱 Navigation
L’application est divisée en 3 fenêtres différentes. 
Pour naviguer entre les fenêtres vous pouvez « swiper » ou sélectionner la fenêtre en bas de votre écran
        - la fenêtre `Carte` ne peut pas être swipée, pour que vous puissiez naviguer sur la carte.

La fenêtre `Info` contient des informations à propos de l’application.
        - "Scroller" pour voir toutes les informations

La fenêtre `Stations` affiche la liste des stations essences d'Île de France
        - "Scroller" pour voir toutes les stations,
        - Visualiser l'adresse d'une station, une image de sa marque, les carburants disponibles et le prix minimal,
        - Ajouter une station en favori (sauvegardé dans la base de données),
        - Cliquer sur les `...` pour voir les détails à propos d'une station,
        - Recherche une station par son adresse ou les carburants disponibles.
                - Cliquer sur la loupe pour lancer la recherche,
                - Cliquer sur la flèche circulaire ou sur la flèche retour du téléphone pour annuler la recherche.

La fenêtre `Détails` affiche toutes les informations à propos de la station 
        - Ajouter la station en favori,
        - Ouvrir la carte avec le pin pour localiser la station essence en question,
        - Fermer la fenêtre avec la croix.

La fenêtre `Carte` vous permet de visualiser la carte google maps des stations essences
        - Zoomer et dézoomer avec le + et le – en abs à droite de l’écran
        - Clustering des pins qui se regrouperont en dézoomant
        - Visualiser l’adresse d'un pin en cliquant dessus
        - Afficher la fenêtre `Détails` en cliquant sur cette adresse
                - En cliquant sur la croix depuis `Détails` vous revenez sur la carte
        - Cliquer sur la flèche en bas à droite pour revenir à la fenêtre `Stations`

Le flèche circulaire en haut à droite de l'écrant vous permet de rafraichir la liste des stations

# 📝 Difficultés rencontrées

Nous avons travaillé avec beaucoup de données pour cette application. 
Malheuresment, le ViewPagerAdapter garde en mémoire les fragement qui ont été créés et ne les supprime jamais.

Ainsi, lorsque nous créiions un nouveau frament ListFragment, qui prennait en argument la liste des stations, nous transmétions une trop grande quantité de données.
Pour résoudre ce problème :
        - Nous créons un fichier `stations.json` qui contient toutes les station,
        - Nous communiquons seulement l'emplacement de ce fichier aux fragments en ayant besoin,
        - Le fragment lit le fichier et récupère les données dont il a besoin.
Ceci nous permet à présent de manipuler une plus grande quantité de données.

