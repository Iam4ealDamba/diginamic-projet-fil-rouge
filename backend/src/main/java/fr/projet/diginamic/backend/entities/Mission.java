package fr.projet.diginamic.backend.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Mission entity class represents a mission assigned to an employee.
 * It includes details about the mission such as start and end dates, the nature of the mission,
 * the starting and destination cities, transport type, and the associated costs etc.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "MISSION")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "libelle", length = 150)
    private String libelle;

    @Min(value = 1)
    @Column(name = "montant_tjm", nullable = false)
    private Double montantTjm;

    @Size(min = 2, max = 100)
    @Column(name = "status", length = 150, nullable = false)
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut", nullable = false)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin", nullable = false)
    private Date dateFin;

    @Size(min = 2, max = 150)
    @Column(name = "transport", length = 150)
    private String transport;

    @Size(min = 2, max = 150)
    @Column(name = "ville_depart", length = 150, nullable = false)
    private String villeDepart;

    @Size(min = 2, max = 150)
    @Column(name = "ville_arrivee", length = 150, nullable = false)
    private String villeArrivee;
    
    @Column(name = "date_prime")
    private Date datePrime;
    
    @Min(value = 1)
    @Column(name = "montant_prime")
    private Double montantPrime;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "nature_mission_id", referencedColumnName = "id", nullable = false)
    private NatureMission natureMission;

    @OneToOne
    @JoinColumn(name = "note_de_frais_id", referencedColumnName = "id")
    private NoteDeFrais noteDeFrais;  

}
