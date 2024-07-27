package fr.projet.diginamic.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class that handles all undefined routes.
 * This is a fallback controller used to return a 404 Not Found status
 * with a message when a request does not match any defined route in the application.
 */
@RestController
@RequestMapping("/api")
public class FallbackController {

     /**
     * Handles all undefined routes under the "/api" path.
     * This method returns a 404 Not Found response with a message indicating
     * that the route was not found.
     *
     * @return a ResponseEntity containing a 404 Not Found status and a message
     */
    @GetMapping("/**")
    public ResponseEntity<String> handleAll() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Route not found.");
    }

}