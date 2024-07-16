package fr.projet.diginamic.backend.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Class representing a regular user */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class UserEntity {
    /** The id of the user */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    /** The firstname of the user */
    @Column(name = "first_name")
    public String firstName;

    /** The lastname of the user */
    @Column(name = "last_name")
    public String lastName;

    /** The birtgdate of the user */
    @Column(name = "birth_date")
    public Date birthDate;

    /** The email of the user */
    @Column(name = "email")
    public String email;

    /** The password of the user */
    @Column(name = "password")
    public String password;

    // Relationships

    /** The manager of the user */
    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id", nullable = true)
    public UserEntity manager;

    /** The collaborators of the user */
    @OneToMany(mappedBy = "manager")
    public List<UserEntity> collaborators;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = true)
    public Role role;

}
