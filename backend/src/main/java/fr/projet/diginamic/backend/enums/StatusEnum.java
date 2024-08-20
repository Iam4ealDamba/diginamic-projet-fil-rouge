package fr.projet.diginamic.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** The type of status */
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
    // JSON creator to handle custom deserialization
    @JsonCreator
    public static StatusEnum fromString(String value) {
        switch (value.toUpperCase()) {
            case "INITIAL":
                return INITIAL;
            case "WAITING":
                return WAITING;
            case "VALIDATED":
                return VALIDATED;
            case "REJECTED":
                return REJECTED;
            case "REFOUNDED":
                return REFOUNDED;
            case "IN_PROGRESS":
            case "EN_COURS": // Handle French value
                return IN_PROGRESS;
            case "FINISHED":
                return FINISHED;
            default:
                throw new IllegalArgumentException("Unknown enum type " + value);
        }
    }

    // JSON value to handle serialization
    @JsonValue
    public String toJson() {
        return this.type;
    }
}
