package fr.projet.diginamic.backend.controllers;

import fr.projet.diginamic.backend.entities.NatureMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * REST Controller for NatureMission entity
 */
@RestController
@RequestMapping("/api/missions/natures")
public class NatureMissionController {
    @Autowired
    private NatureMissionService service;

    @GetMapping
    public List<NatureMission> getAllNaturesMissions() {
        return service.findAll();
    }
}
