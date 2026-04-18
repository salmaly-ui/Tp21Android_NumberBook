# 📱 PhoneBook Pro

## 🚀 Application Android (Java + Retrofit + PHP/MySQL)

Application mobile permettant de lire les contacts du téléphone, les afficher dans une interface moderne et les synchroniser avec une base de données distante via une API REST.

---

## 🧰 Stack technique

* Langage : Java (Android)
* Réseau : Retrofit 2 + OkHttp + Gson
* UI : RecyclerView + Material Design
* Serveur : PHP 8 (API REST)
* Base de données : MySQL / MariaDB
* Serveur local : XAMPP (Apache + MySQL)

---

## 🎥 Démonstration

🔗 Vidéo :


https://github.com/user-attachments/assets/69245b7e-82f7-4d6b-b86d-04b150449ae6



---

## 📁 Structure du projet
```
PhoneBook Pro/
├── android/
│   ├── Personne.java
│   ├── ReponseApi.java
│   ├── PersonneApi.java
│   ├── ClientRetrofit.java
│   ├── AdaptateurPersonne.java
│   ├── MainActivity.java
│   └── res/
│       ├── layout/
│       └── drawable/
│
└── phonebook-api/
├── config/
│   └── ConnexionBD.php
├── modele/
│   └── Personne.php
├── gestionnaire/
│   └── GestionnairePersonne.php
└── api/
├── listerPersonnes.php
└── rechercherPersonne.php
```
---

## ⚙️ Prérequis

### Android

* Android Studio
* SDK Android 21+
* Téléphone + PC sur le même Wi-Fi

### Serveur

* XAMPP (Apache + MySQL)
* PHP 8+
* MySQL 5.7+ / MariaDB

---

## 📥 Installation

### 1. Base de données
```sql
CREATE DATABASE IF NOT EXISTS phonebook
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE phonebook;

CREATE TABLE personne (
ref INT AUTO_INCREMENT PRIMARY KEY,
nom VARCHAR(160) NOT NULL,
telephone VARCHAR(60) NOT NULL,
origine VARCHAR(60) DEFAULT 'android',
date_ajout DATETIME DEFAULT CURRENT_TIMESTAMP
);
```
---

### 2. Backend PHP

Copier le projet dans :
C:\xampp\htdocs\phonebook-api\

Configurer ConnexionBD.php :
```
private $serveur = 'localhost';
private $baseDeDonnees = 'phonebook';
private $login = 'root';
private $motDePasse = '';
```
Tester :
http://localhost/phonebook-api/api/listerPersonnes.php

---

### 3. Application Android

Trouver IP du PC :

ipconfig

Modifier ClientRetrofit.java :
```
private static final String ADRESSE_API =
"http://192.168.X.X/phonebook-api/api/";
```
Permissions Android :
```
<uses-permission android:name="android.permission.READ_CONTACTS"/>  
<uses-permission android:name="android.permission.INTERNET"/>

<application android:usesCleartextTraffic="true">
```
---

## ▶️ Lancement

* Lancer XAMPP (Apache + MySQL)
* Lancer l’application Android

---

## 🌐 Fonctionnalités

✔ Import des contacts du téléphone
✔ Affichage avec RecyclerView
✔ Synchronisation vers MySQL
✔ Recherche distante (nom / téléphone)

---

## 🔄 API REST

GET     /listerPersonnes.php
GET     /rechercherPersonne.php?terme=xxx

---

## 🔍 Recherche

* Recherche partielle
* Insensible à la casse
* Filtre par nom ou téléphone

---

##  UI / Design

* Cartes modernes
* Design Material
* Liste fluide (RecyclerView)
* Inputs stylés

---

## 🧠 Fonctionnement

1. Lecture des contacts
2. Affichage RecyclerView
3. Synchronisation API REST
4. Stockage MySQL
5. Recherche distante

---

## 🚀 Améliorations possibles

* Détection des doublons
* Barre de progression
* Mode hors ligne (Room)
* Authentification
* HTTPS

---

## 👨‍💻 Auteur
Salma Laouy
Projet Android + PHP/MySQL (TP développement mobile)
