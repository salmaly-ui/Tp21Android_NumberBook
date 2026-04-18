<?php
require_once __DIR__ . '/../config/ConnexionBD.php';

class GestionnairePersonne {
    private $lien;
    private $table = 'personne';

    public function __construct() {
        $bd = new ConnexionBD();
        $this->lien = $bd->ouvrir();
    }

    public function ajouter($nom, $telephone, $origine = 'android') {
        $sql = 'INSERT INTO ' . $this->table
             . ' (nom, telephone, origine)'
             . ' VALUES (:nom, :telephone, :origine)';
        $req = $this->lien->prepare($sql);
        return $req->execute([
            ':nom'       => $nom,
            ':telephone' => $telephone,
            ':origine'   => $origine
        ]);
    }

    public function tousLesContacts() {
        $sql = 'SELECT * FROM ' . $this->table . ' ORDER BY nom ASC';
        $req = $this->lien->prepare($sql);
        $req->execute();
        return $req->fetchAll(PDO::FETCH_ASSOC);
    }

    public function rechercher($motCle) {
        $sql = 'SELECT * FROM ' . $this->table
             . ' WHERE nom LIKE :motCle OR telephone LIKE :motCle'
             . ' ORDER BY nom ASC';
        $req = $this->lien->prepare($sql);
        $req->execute([':motCle' => '%' . $motCle . '%']);
        return $req->fetchAll(PDO::FETCH_ASSOC);
    }
}
?>
