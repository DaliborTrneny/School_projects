<?php
// Application middleware

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

$app->add(function(Request $request, Response $response, $next) {
    $basePath = $request->getUri()->getBasePath();

    if (isset($_SESSION['logged_user'])) {
        $this->view->addParam('logged_user', $_SESSION['logged_user']);
    }

    $this->view->addParam('basePath', $basePath);
    return $next($request, $response);
});