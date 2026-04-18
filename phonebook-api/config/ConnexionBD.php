<?php
class ConnexionBD {
    private $serveur   = 'localhost';
    private $baseDeDonnees = 'phonebook';
    private $login     = 'root';
    private $motDePasse = '1234';
    public  $lien;

    public function ouvrir() {
        $this->lien = null;
        try {
            $dsn = 'mysql:host=' . $this->serveur
                 . ';dbname=' . $this->baseDeDonnees
                 . ';charset=utf8mb4';
            $this->lien = new PDO($dsn, $this->login, $this->motDePasse);
            $this->lien->setAttribute(
                PDO::ATTR_ERRMODE,
                PDO::ERRMODE_EXCEPTION
            );
        } catch (PDOException $e) {
            echo 'Echec de connexion : ' . $e->getMessage();
        }
        return $this->lien;
    }
}
?>
