package fr.projet.diginamic.backend.dtos;

import fr.projet.diginamic.backend.enums.TransportEnum;
import fr.projet.diginamic.backend.enums.StatusEnum;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object for displaying mission information in the frontend.
 * Excludes sensitive bonus details and simplifies user information to user ID.
 * Utilizes Lombok annotations to reduce boilerplate code for getters and
 * setters.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateMissionDTO {
    private Long userId;
    private String label;
    private String labelNatureMission;
    private StatusEnum status;
    private Date startDate;
    private Date endDate;
    private TransportEnum transport;
    private String departureCity;
    private String arrivalCity;
}
