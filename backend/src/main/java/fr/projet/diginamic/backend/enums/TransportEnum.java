package fr.projet.diginamic.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransportEnum {
    AVION("Avion"),
    COVOITURAGE("Covoiturage"),
    TRAIN("Train"),
    VOITURESERVICE("Voiture de service");

    // Attibutes
    /** The type of transport */
    private final String type;

}
