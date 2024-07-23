package fr.projet.diginamic.backend.entities;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class representing the nature of a mission
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class NatureMission {

    /** The id of the nature of the mission */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The label of the nature of the mission */
    @Column(name = "label")
    private String label;

    /** The ceiling TJM of the nature of the mission */
    @Column(name = "Adr")
    private Double adr;

    /** The billing status of the nature of the mission */
    @Column(name = "is_billed")
    private Boolean isBilled;

    /** The start date of the nature of the mission */
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    /** The end date of the nature of the mission */
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    /** The bonus percentage of the nature of the mission */
    @Column(name = "bonus_percentage")
    private Double bonusPercentage;

    @Column(name = "is_eligible_to_bounty")
    private Boolean isEligibleToBounty;

    /** The missions associated with this nature */
    @OneToMany(mappedBy = "natureMission")
    private Set<Mission> missions;
}
