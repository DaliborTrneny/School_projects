<?php
function newLocation($app, $formData) {
    $stmt = $app->db->prepare('INSERT INTO location (street_name, street_number, zip, city) VALUES (:sname, :snum, :zip, :city)');

    $stmt->bindValue(':sname', empty($formData['street_name']) ? null : $formData['street_name']);
    $stmt->bindValue(':snum', empty($formData['street_number']) ? null : $formData['street_number']);
    $stmt->bindValue(':city', empty($formData['city']) ? null : $formData['city']);
    $stmt->bindValue(':zip', empty($formData['zip']) ? null : $formData['zip']);

    $stmt->execute();

    $id_location = $app->db->lastInsertId();
    return $id_location;
}

function restartSession($app) {

    $statement = $app->db->prepare('SELECT * FROM colonist WHERE id_colonist = :idc');

    $statement->bindValue(':idc', $_SESSION['logged_user']['id_colonist']);
    $statement->execute();

    $logged_user = $statement->fetch();

    $_SESSION['logged_user'] = $logged_user;
}

?>