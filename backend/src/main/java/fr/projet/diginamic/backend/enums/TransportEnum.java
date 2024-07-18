package fr.projet.diginamic.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransportEnum {
    AIRPLANE("Air Plane"),
    CARPOOLING("Carpooling"),
    TRAIN("Train"),
    SERVICE_CAR("Service Car");

    // Attibutes
    /** The type of transport */
    private final String type;

}
