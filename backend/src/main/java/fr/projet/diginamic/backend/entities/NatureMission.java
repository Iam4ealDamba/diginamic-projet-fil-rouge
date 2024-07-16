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
    @Column(name = "libelle")
    private String libelle;

    /** The plafond TJM of the nature of the mission */
    @Column(name = "plafond_tjm")
    private Double plafondTjm;

    /** The facturation of the nature of the mission */
    @Column(name = "facturation")
    private Boolean facturation;

    /** The debut prime of the nature of the mission */
    @Column(name = "debut_prime")
    private Date debutPrime;

    /** The fin prime of the nature of the mission */
    @Column(name = "fin_prime")
    private Date finPrime;

    /** The pourcentage prime of the nature of the mission */
    @Column(name = "pourcentage_prime")
    private Double pourcentagePrime;

    /** The missions associated with this nature */
    @OneToMany(mappedBy = "natureMission")
    private Set<Mission> missions;
}
