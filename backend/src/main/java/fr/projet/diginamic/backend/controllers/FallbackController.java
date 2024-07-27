package fr.projet.diginamic.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FallbackController {

    @GetMapping("/**")
    public ResponseEntity<String> handleAll() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Route not found.");
    }

}