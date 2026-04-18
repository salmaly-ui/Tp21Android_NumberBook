<?php
header('Content-Type: application/json');
require_once __DIR__ . '/../gestionnaire/GestionnairePersonne.php';

$gestionnaire = new GestionnairePersonne();
echo json_encode($gestionnaire->tousLesContacts());
?>
