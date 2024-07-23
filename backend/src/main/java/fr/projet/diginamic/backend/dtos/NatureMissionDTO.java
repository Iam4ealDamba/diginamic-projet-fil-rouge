package fr.projet.diginamic.backend.dtos;

import lombok.*;

import java.util.Date;

/**
 * Data Transfer Object for NatureMission entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NatureMissionDTO {

    private Long id;
    private String label;
    private Double adr;
    private Boolean isBilled;
    private Date startDate;
    private Date endDate;
    private Double bonusPercentage;
    private Boolean isEligibleToBounty;

}