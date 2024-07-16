package fr.projet.diginamic.backend.entities;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class User {

    /** The id of the user */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    /** The firstname of the user */
    @Column(name = "firstname")
    public String firstName;

    /** The lastname of the user */
    @Column(name = "lastname")
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

    /** The manager of the user */
    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id", nullable = true)
    public User manager;

    /** The employees of the user */
    @OneToMany(mappedBy = "manager")
    public Set<User> employees;

}
