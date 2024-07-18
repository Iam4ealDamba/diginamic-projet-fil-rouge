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

    private Integer id;
    private String label;
    private Double ceilingTjm;
    private Boolean billing;
    private Date startDate;
    private Date endDate;
    private Double bonusPercentage;
}
