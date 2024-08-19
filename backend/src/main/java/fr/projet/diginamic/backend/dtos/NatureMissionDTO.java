package fr.projet.diginamic.backend.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private Double Adr;
    private Boolean IsBilled;
    private Date startDate;
    private Date endDate;
    private Double BountyRate;
    private Boolean isEligibleToBounty;


}

