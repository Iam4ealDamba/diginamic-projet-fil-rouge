package fr.projet.diginamic.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {
    INITIAL("Initial"),
    WAITING("Waiting"),
    VALIDATED("Validated"),
    REJECTED("Rejected"),
    REFOUNDED("Refounded"),
    IN_PROGRESS("In Progress"),
    FINISHED("Finished");

    // Attibutes
    /** The type of status */
    private final String type;
}
