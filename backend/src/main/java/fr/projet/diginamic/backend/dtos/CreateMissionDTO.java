package fr.projet.diginamic.backend.dtos;

import fr.projet.diginamic.backend.enums.TransportEnum;
import fr.projet.diginamic.backend.enums.StatusEnum;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object for displaying mission information in the frontend.
 * Excludes sensitive bounty details and simplifies user information to user ID.
 * Utilizes Lombok annotations to reduce boilerplate code for getters and
 * setters.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateMissionDTO {
       /** The label or name of the mission */
       private String label;

       /** The current status of the mission */
       private StatusEnum status;
   
       /** The start date of the mission */
       private Date startDate;
   
       /** The end date of the mission */
       private Date endDate;
   
       /** The transport mode used for the mission */
       private TransportEnum transport;
   
       /** The city from which the mission starts */
       private String departureCity;
   
       /** The city at which the mission ends */
       private String arrivalCity;
   
       /** The unique identifier of the user assigned to the mission */
       private Long userId;
   
       /** The unique identifier of the nature of the mission */
       private Long natureMissionId;   
}
