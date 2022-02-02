<?php

include 'utils.php';

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

$app->get('/', function (Request $request, Response $response, $args) {
    // Render index view
    return $this->view->render($response, 'login.latte');
})->setName('index');

// Users page endpoint
$app->get('/users', function (Request $request, Response $response, $args) {
    $params = $request->getQueryParams();

    if (empty($params['query'])) {

        $statement = $this->db->prepare('SELECT * FROM colonist WHERE id_habitat = :idh ORDER BY id_colonist');

        $statement->bindParam(':idh', $_SESSION['logged_user']['id_habitat']);

        $statement->execute();
        $data['colonists'] = $statement->fetchall();


        return $this->view->render($response, 'users.latte', $data);

    } else {

        $statement = $this->db->prepare('SELECT * FROM colonist 
            WHERE Lower(first_name) = Lower(:fn) OR Lower(last_name) = Lower(:ln)'
        );

        $statement->bindParam(':fn', $params['query']);
        $statement->bindParam(':ln', $params['query']);

    }

    $statement->execute();
    $data['colonists'] = $statement->fetchall();
    return $this->view->render($response, 'users.latte', $data);

})->setName('users_list');

//Habitat page endpoint
$app->get('/habitats', function (Request $request, Response $response, $args) {
    $params = $request->getQueryParams();

    if (empty($params['query'])) {

        $statement = $this->db->prepare('SELECT * FROM habitat ORDER BY id_habitat');

        $statement->execute();
        $data['habitats'] = $statement->fetchall();


        return $this->view->render($response, 'habitats.latte', $data);

    } else {

        $statement = $this->db->prepare('SELECT * FROM habitat 
            WHERE Lower(habitat_name) = Lower(:hn) OR Lower(id_habitat) = Lower(:ih)'
        );

        $statement->bindParam(':hn', $params['query']);
        $statement->bindParam(':ih', $params['query']);

    }

    $statement->execute();
    $data['habitats'] = $statement->fetchall();
    return $this->view->render($response, 'habitats.latte', $data);

})->setName('habitats_list');

$app->group('/auth', function() use($app) {
    // zobrazeni profilu
    $app->get('/profile/{id}', function (Request $request, Response $response, $args) {
        $stmt = $this->db->prepare('SELECT * FROM colonist WHERE id_colonist = :idc');
        $stmt->bindValue(':idc', $args['id']);
        $stmt->execute();

        $data['colonist'] = $stmt->fetch();


        return $this->view->render($response, 'profile.latte', $data);
    })->setName('colonist_profile');

    // edit kolonisty
    $app->get('/colonist/{id}/edit', function (Request $request, Response $response, $args) {
        $stmt = $this->db->prepare('SELECT * FROM colonist LEFT JOIN habitat ON colonist.id_habitat = habitat.id_habitat WHERE id_colonist = :idc');
        $stmt->bindValue(':idc', $args['id']);
        $stmt->execute();

        $data['colonist'] = $stmt->fetch();

        $statement = $this->db->prepare('SELECT id_habitat, habitat_name FROM habitat');
        $statement->execute();

        $data['habitats']= $statement->fetchAll();

        return $this->view->render($response, 'edit_colonist.latte', $data);
    })->setName('edit_colonist');

    // zpracovani uzivatele
    $app->post('/colonist/{id}/edit', function (Request $request, Response $response, $args) {

        $statement = $this->db->prepare('SELECT colonist_role, id_habitat FROM colonist WHERE id_colonist = :id');

        $statement->bindValue(':id', $args['id']);

        $statement->execute();

        $authorization = $statement->fetch();

        $formData = $request->getParsedBody();
        $data['formData'] = $formData;
        $data['formData']['id_colonist'] = $args['id'];

        try {
            $stmt = $this->db->prepare('UPDATE colonist SET
                                first_name = :fn,
                                last_name = :ln,
                                gender = :gn,
                                colonist_role = :rl, 
                                id_habitat = :idh
                            WHERE id_colonist = :idc');

            $stmt->bindValue(':fn', $formData['first_name']);
            $stmt->bindValue(':ln', $formData['last_name']);
            $stmt->bindValue(':gn', $formData['gender']);
            $stmt->bindValue(':rl', ($_SESSION['logged_user']['colonist_role'] != 'supervisor') ? $authorization['colonist_role'] : $formData['colonist_role']);
            $stmt->bindValue(':idh', ($_SESSION['logged_user']['colonist_role'] == 'colonist') ? $authorization['id_habitat'] : $formData['id_habitat']);
            $stmt->bindValue(':idc', $args['id']);

            $stmt->execute();

            restartSession($this);
        } catch (Exception $e) {
            $data['message'] = 'Something went wrong. Don\'t give up, try it again!';
        }

        return $response->withRedirect($this->router->pathFor('colonist_profile', $args));
    })->setName('edit_colonist');

    // edit habitatu
    $app->get('/habitat/{id}/edit', function (Request $request, Response $response, $args) {
        $stmt = $this->db->prepare('SELECT * FROM habitat WHERE id_habitat = :idh');
        $stmt->bindValue(':idh', $args['id']);
        $stmt->execute();

        $data['habitat'] = $stmt->fetch();

        $actualPopulation = $this->db->prepare('SELECT COUNT(*) AS population FROM colonist LEFT JOIN habitat ON habitat.id_habitat = colonist.id_habitat WHERE habitat.id_habitat = :idh GROUP BY habitat.id_habitat');

        $actualPopulation->bindValue(':idh', $args['id']);
        $actualPopulation->execute();
        $data['actualPopulation'] = $actualPopulation->fetch();


        return $this->view->render($response, 'edit_habitat.latte', $data);
    })->setName('edit_habitat');

    // zpracovani habitatu
    $app->post('/habitat/{id}/edit', function (Request $request, Response $response, $args) {

        $statement = $this->db->prepare('SELECT max_population FROM habitat WHERE id_habitat = :id');

        $statement->bindValue(':id', $args['id']);

        $statement->execute();

        $authorization = $statement->fetch();

        $actualPopulation = $this->db->prepare('SELECT COUNT(*) AS population FROM colonist LEFT JOIN habitat ON habitat.id_habitat = colonist.id_habitat WHERE habitat.id_habitat = :idh GROUP BY habitat.id_habitat');

        $actualPopulation->bindValue(':idh', $args['id']);
        $actualPopulation->execute();
        $data['actual_population'] = $actualPopulation->fetch();

        $formData = $request->getParsedBody();
        $data['formData'] = $formData;
        $data['formData']['id_habitat'] = $args['id'];

        try {
            $stmt = $this->db->prepare('UPDATE habitat SET
                                habitat_name = :hn,
                                x_coordinate = :xc,
                                y_coordinate = :yc,
                                max_population = :mp 
                            WHERE id_habitat = :idh');

            $stmt->bindValue(':hn', $formData['habitat_name']);
            $stmt->bindValue(':xc', $formData['x_coordinate']);
            $stmt->bindValue(':yc', $formData['y_coordinate']);
            $stmt->bindValue(':mp', ($formData['max_population'] < $data['actual_population']['population']) ? $authorization['max_population'] : $formData['max_population']);
            $stmt->bindValue(':idh', $args['id']);

            $stmt->execute();

            restartSession($this);
        } catch (Exception $e) {
            $data['message'] = 'Something went wrong. Don\'t give up, try it again!';
        }    
    
        
        return $response->withRedirect($this->router->pathFor('habitats_list', $args));
    })->setName('edit_habitat');

    // logout
    $app->get('/logout', function (Request $request, Response $response, $args) {
        session_destroy();
        return $response->withHeader('Location', $this->router->pathFor('index'));
    })->setName('logout');

    $app->get('/colonist/{id}/delete', function (Request $request, Response $response, $args) {
        try {
    
            $statement = $this->db->prepare('DELETE FROM colonist WHERE id_colonist = :id');
            $statement->bindParam(':id', $args['id']);
            $statement->execute();
    
        } catch (Exception $e) {
            $this->logger->error($e);
        }
    
        return $response->withHeader('Location', $this->router->pathFor('users_list'));
    
    })->setName('delete_colonist');


    // MAP GETTING READY
    $app->get('/habitats/map', function (Request $request, Response $response, $args) {
        $statement = $this->db->prepare('SELECT * FROM habitat');
        $statement->execute();
        $data['habitatsRep'] = $statement->fetchAll();

        return $this->view->render($response, 'habitats_map.latte', $data);
    })->setName('habitat_map');

    // Nacteni user do DB
    $app->get('/colonist/new', function (Request $request, Response $response, $args) {
        $data['formData'] = [
            'first_name' => '',
            'last_name' => '',
            'colonist_role' => '',
            'gender' => '',
            'id_habitat' => ''
        ];

        $statement = $this->db->prepare('SELECT id_habitat, habitat_name FROM habitat');
        $statement->execute();

        $data['habitats']= $statement->fetchAll();

        return $this->view->render($response, 'colonist_new.latte', $data);
    })->setName('colonist_new');

    $app->post('/colonist/new', function (Request $request, Response $response, $args) {
        $formData = $request->getParsedBody(); 

        $actualPopulation = $this->db->prepare('SELECT COUNT(*) AS population FROM colonist LEFT JOIN habitat ON habitat.id_habitat = colonist.id_habitat WHERE habitat.id_habitat = :idh GROUP BY habitat.id_habitat');

        $actualPopulation->bindValue(':idh', $formData['id_habitat']);
        $actualPopulation->execute();
        $data['actual_population'] = $actualPopulation->fetch();

        $maxPopulation = $this->db->prepare('SELECT max_population FROM habitat WHERE id_habitat = :idh');
        $maxPopulation->bindValue(':idh', $formData['id_habitat']);
        $maxPopulation->execute();
        $data['maximal_population'] = $maxPopulation->fetch();
    
        if (empty($formData['first_name']) || empty($formData['last_name']) || empty($formData['gender']) || empty($formData['colonist_role']) || empty($formData['id_habitat'])) {
            $data['message'] = 'Please fill required fields';
        } else {

            echo(var_dump($data['maximal_population']['max_population']));
            echo(var_dump($data['actual_population']['population']));

            try {
                if ($data['actual_population']['population'] < $data['maximal_population']['max_population']) {
                    $stmt = $this->db->prepare('INSERT INTO colonist (first_name, last_name, gender, colonist_role, id_habitat) VALUES (:fn, :ln, :gn, :cr, :idh)');
                    $stmt->bindValue(':fn', $formData['first_name']);
                    $stmt->bindValue(':ln', $formData['last_name']);
                    $stmt->bindValue(':gn', $formData['gender']);
                    $stmt->bindValue(':cr', $formData['colonist_role']);
                    $stmt->bindValue(':idh', $formData['id_habitat']);
                    
                    $stmt->execute();
    
                    $data['message'] = 'Colonist succesfully inserted';
                } else {
                    $data['message'] = 'Capacity of habitat is full';
                }
                
            } catch (Exception $e) {
                $data['message'] = 'Something went wrong, try it again';
            }
        }
    
        $data['formData'] = $formData;
    
        return $this->view->render($response, 'colonist_new.latte', $data);
    });

    // Nacteni habitatu do DB
    $app->get('/habitat/new', function (Request $request, Response $response, $args) {
        $data['formData'] = [
            'habitat_name' => '',
            'x_coordinate' => '',
            'y_coordinate' => '',
            'max_population' => '',
            'id_officer' => ''
        ];

        $statement = $this->db->prepare('SELECT * FROM colonist WHERE colonist.colonist_role = :cr');
        $statement->bindValue(':cr', 'colonist');

        $statement->execute();

        $data['officers'] = $statement->fetchAll();

        return $this->view->render($response, 'habitat_new.latte', $data);
    })->setName('habitat_new');

    $app->post('/habitat/new', function (Request $request, Response $response, $args) {
        $formData = $request->getParsedBody(); 
    
        if (empty($formData['habitat_name']) || empty($formData['x_coordinate']) || empty($formData['y_coordinate']) || empty($formData['max_population']) || empty($formData['id_officer'])) {
            $data['message'] = 'Please fill required fields';
        } else {
            try {
                $stmt = $this->db->prepare('INSERT INTO habitat (habitat_name, x_coordinate, y_coordinate, max_population, id_officer) VALUES (:hn, :xc, :yc, :mp, :ido)');
                $stmt->bindValue(':hn', $formData['habitat_name']);
                $stmt->bindValue(':xc', $formData['x_coordinate']);
                $stmt->bindValue(':yc', $formData['y_coordinate']);
                $stmt->bindValue(':mp', $formData['max_population']);
                $stmt->bindValue(':ido', $formData['id_officer']);

                $data['id_colonist'] = $formData['id_officer'];
                
                $stmt->execute();

                $colonistHandling = $this->db->prepare('UPDATE colonist SET id_habitat = :idh, colonist_role = :cr WHERE id_colonist = :idc');

                $colonistHandling->bindValue(':idc', $formData['id_officer']);
                $colonistHandling->bindValue(':cr', 'officer');
                $colonistHandling->bindValue(':idh', $data['id_colonist']);

                $colonistHandling->execute();


                $data['message'] = 'Habitat succesfully inserted';
            } catch (Exception $e) {
                $data['message'] = 'Something went wrong, try it again (check coordinates: x <= 1290 and y <= 790)';
            }
        }
    
        $data['formData'] = $formData;
    
        return $this->view->render($response, 'habitat_new.latte', $data);
    });

})->add(function($request, $response, $next) {
    if (!empty($_SESSION['logged_user'])) {
        return $next($request, $response);
    } else {
        return $response->withHeader('Location', $this->router->pathFor('login_form'));
    }
});

// Login page endpoint
$app->get('/login', function (Request $request, Response $response, $args) {
    return $this->view->render($response, 'login.latte');
})->setName('login_form');

$app->get('/home', function (Request $request, Response $response, $args) {
    return $this->view->render($response, 'homepage.latte');
})->setName('homepage');

// zpracovava formular
$app->post('/', function (Request $request, Response $response, $args) {
    $formData = $request->getParsedBody();

    // tvorba hashe
    $passwd_hash = hash('sha256', $formData['password']);

    // overeni uzivatele v databazi
    $statement = $this->db->prepare('SELECT * FROM colonist WHERE Lower(last_name) = Lower(:nn) AND password = :pswd');

    $statement->bindValue(':nn', $formData['last_name']);
    $statement->bindValue(':pswd', $passwd_hash);

    $statement->execute();
    
    $logged_user = $statement->fetch();

    if ($logged_user) {
        $_SESSION['logged_user'] = $logged_user;
        return $response->withHeader('Location', $this->router->pathFor('homepage'));
    } else {
        return $this->view->render($response, 'login.latte', ['message' => 'Invalid creditentials']);
    }

    return $this->view->render($response, 'login.latte', $formData);
});
