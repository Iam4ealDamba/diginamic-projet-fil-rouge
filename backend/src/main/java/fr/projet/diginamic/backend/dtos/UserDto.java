package fr.projet.diginamic.backend.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Data transfer object of the user entity */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    /** The id of the user */
    private Long id;

    /** The firstname of the user */
    private String firstName;

    /** The lastname of the user */
    private String lastName;

    /** The birtgdate of the user */
    private Date birthDate;

    /** The email of the user */
    private String email;
}
