package fr.projet.diginamic.backend.dtos;

import java.util.Date;

import fr.projet.diginamic.backend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Data transfer object of the user entity */
@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {
    /** The firstname of the user */
    private String firstName;

    /** The lastname of the user */
    private String lastName;

    /** The birtgdate of the user */
    private Date birthDate;

    /** The email of the user */
    private String email;

    /** The password of the user */
    private String password;

    /** The role of the user */
    private String role;

    /** Constructor with all args */
    public RegisterDto(String firstName, String lastName, Date birthDate, String email, String password,
            String... role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.role = role.length > 0 ? role[0] : "USER";
    }
}
