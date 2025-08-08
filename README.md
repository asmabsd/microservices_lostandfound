🎥 [DEMO] Smart Lost & Found – Une plateforme intelligente basée sur l’architecture microservices
🔍 Présentation
Smart Lost & Found est un projet personnel que j’ai développé pour maîtriser en profondeur l’architecture microservices, de la conception à la mise en production sur Azure.
Au-delà de l’aspect technique, ce projet répond à un besoin concret et local : en Tunisie, la majorité des personnes utilisent encore Facebook pour signaler ou rechercher un objet perdu – une méthode peu fiable et non structurée.

👉 C’est dans ce contexte que Smart Lost & Found prend tout son sens : proposer une plateforme web intelligente dédiée à la gestion des objets perdus et trouvés, de manière centralisée, rapide et efficace.

⚙️ Fonctionnalités clés
👤 Gestion des utilisateurs
Inscription / Connexion sécurisée

Modification du profil (photo, informations personnelles)

Rôle utilisateur : normal ou premium

🧾 Gestion des objets perdus/trouvés
Publication d’un objet avec photo, description et lieu

Filtrage et recherche d’objets par catégorie, date, lieu

Système de matching automatique basé sur l’IA

💬 Réclamations et notifications
Possibilité de déclarer un objet retrouvé ou perdu

Suivi des réclamations via tableau de bord

Notification automatique par email

🎯 Gamification et récompenses
Système de score : chaque action rapporte des points

Exemple : 1000 points = 1 mois d’abonnement premium gratuit

🚀 Stack Technique
🧩 Backend (Java Spring Boot – Microservices)
user-service – gestion des utilisateurs

item-service – gestion des objets

matching-service – matching automatique via IA

notification-service – envoi des emails

reclamation-service – réclamations

payment-service – gestion des paiements (abonnements premium)

ai-service – service d’intelligence artificielle (analyse d’image/texte)

api-gateway – point d’entrée unique

service-registry – Eureka pour la découverte de services

🌐 Frontend (Angular)
Interface responsive et moderne

Tableau de bord utilisateur/admin

Intégration avec tous les microservices

☁️ DevOps & Cloud
Déploiement sur Azure Container Apps

Docker + Docker Compose pour l’environnement local

Base de données : MySQL

🖥️ Comment exécuter le projet en local
🔁 Pré-requis
Java 17+

Node.js / npm

Angular CLI

Docker (recommandé)

📥 Étapes
Cloner les projets :

Backend (Spring Boot) : cloner tous les microservices disponibles dans /backend

Frontend (Angular) : cloner le projet Angular dans /frontend

Démarrer les microservices Spring Boot :

Utiliser IntelliJ ou VS Code

Lancer les services un par un ou via Docker Compose

Lancer le frontend Angular :

npm install
ng serve
Accéder à l’application :
👉 http://localhost:4200

NB : N'oublier pas de lancer le script python 
accéder a l'emplacement du fichier ai_service.py 
taper dans cmd 
python ai_service.py 

📷 Aperçu visuel
<img src="demo-screenshot.png" alt="Aperçu Smart Lost & Found" width="600"/>
💡 Objectifs atteints
Mise en œuvre complète de l'architecture microservices

Utilisation d’outils DevOps modernes

Déploiement sur le cloud Azure

Création d’un service IA de matching image/texte

Réponse à un vrai besoin sociétal en Tunisie

📬 Contact
Pour toute question ou collaboration :
📧 contact@lostandfound.com

🛠️ Stack technique :

Microservices : api-gateway, eureka, config-server, user/item/payment/notification/matching-service

Docker + Kubernetes (déploiement via ./deploy.sh)

Angular + API Gateway exposés via IP publique (Azure – pack étudiant)

Démo vidéo + captures d’écran à découvrir 👇

🧠 Ce projet a été une véritable aventure d’apprentissage, en m'appuyant sur :

📺 Tuto YouTube de Bouali Ali (https://www.youtube.com/watch?v=jdeSV0GRvwI&t=20352s)

📖 Blog SparkFabrik – Microservices vs Monolithique(https://blog.sparkfabrik.com/en/microservices-and-cloud-native-applications-vs-monolithic-applications)

📘 Guide Java – Architecture Microservices


(https://www.javaguides.net/2024/05/angular-spring-boot-microservices.html)

lien repo github ( spring boot ) : https://github.com/asmabsd/microservices_lostandfound

lien repo github ( angular ) : 

https://github.com/asmabsd/lostandfoundfrontend

les fichiers de déploiement sont désormais disponible dans le repo de spring boot 

ci joint mon démonstration et des captures d 'écran démonstratives 
<img width="631" height="283" alt="1" src="https://github.com/user-attachments/assets/6cfe340c-8aae-40ef-a2ab-0bb8ea2dc2a7" />
<img width="629" height="208" alt="2" src="https://github.com/user-attachments/assets/c373e982-7958-40e8-9147-0083e94fc068" />
<img width="959" height="410" alt="3" src="https://github.com/user-attachments/assets/aee49485-f632-48fc-aa10-b07b2d05ab3f" />
<img width="806" height="233" alt="4" src="https://github.com/user-attachments/assets/a2eee51c-eeca-421b-a7b0-acb1d665b0a7" />
video de demo : 
https://www.linkedin.com/posts/asma-boussaada_demo-smart-lost-found-un-projet-activity-7359235162091442177-CCGx?utm_source=share&utm_medium=member_desktop&rcm=ACoAAD3RDdMB8MFhymwcy4xqrFha_pEh8kF0PrE


©️ Mentions légales
Ce projet a été réalisé par Asma Boussaada dans un cadre personnel, éducatif et expérimental.

Vous êtes libre de vous en inspirer pour créer vos propres projets, apprendre ou expérimenter. Cependant, merci de ne pas copier intégralement l’idée ou le projet tel quel.
Prenez ce projet comme une base d’inspiration pour développer vos propres solutions, avec votre propre vision, vos fonctionnalités et vos améliorations.

