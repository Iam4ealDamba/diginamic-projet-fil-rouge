package fr.projet.diginamic.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {
    INITIAL("Initial"),
    EN_ATTENTE("En Attente"),
    VALIDE("Validé"),
    REJETE("Rejeté"),
    REMBOURSE("Remboursé"),
    EN_COURS("En cours"),
    TERMINE("Termine");

    // Attibutes
    /** The type of status */
    private final String type;
}
