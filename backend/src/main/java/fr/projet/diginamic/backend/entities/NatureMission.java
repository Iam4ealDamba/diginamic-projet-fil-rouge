package fr.projet.diginamic.backend.entities;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private Integer id;

    /** The label of the nature of the mission */
    @Column(name = "label")
    private String label;

    /** The ceiling TJM of the nature of the mission */
    @Column(name = "ceiling_tjm")
    private Double ceilingTjm;

    /** The billing status of the nature of the mission */
    @Column(name = "billing")
    private Boolean billing;

    /** The start date of the nature of the mission */
    @Column(name = "start_date")
    private Date startDate;

    /** The end date of the nature of the mission */
    @Column(name = "end_date")
    private Date endDate;

    /** The bonus percentage of the nature of the mission */
    @Column(name = "bonus_percentage")
    private Double bonusPercentage;

    /** The missions associated with this nature */
    @OneToMany(mappedBy = "natureMission")
    private Set<Mission> missions;
}
