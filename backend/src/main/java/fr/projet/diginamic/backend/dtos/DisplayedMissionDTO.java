package fr.projet.diginamic.backend.dtos;

import java.util.Date;

import fr.projet.diginamic.backend.enums.StatusEnum;
import fr.projet.diginamic.backend.enums.TransportEnum;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@ToString
public class DisplayedMissionDTO {
    private Long id;
    private String label;
    private Double totalPrice;
    private StatusEnum status;
    private Date startDate;
    private Date endDate;
    private TransportEnum transport;
    private String departureCity;
    private String arrivalCity;
    private Date bountyDate;
    private Double bountyAmount;
    private String labelNatureMission;
    private Long userId;
    private Long natureMissionId;
    private Long expenseId;
}
