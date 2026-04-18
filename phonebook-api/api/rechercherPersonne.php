<?php
header('Content-Type: application/json');
require_once __DIR__ . '/../gestionnaire/GestionnairePersonne.php';

if (empty($_GET['terme'])) {
    echo json_encode([]);
    exit;
}

$terme = $_GET['terme'];
$gestionnaire = new GestionnairePersonne();
echo json_encode($gestionnaire->rechercher($terme));
?>
