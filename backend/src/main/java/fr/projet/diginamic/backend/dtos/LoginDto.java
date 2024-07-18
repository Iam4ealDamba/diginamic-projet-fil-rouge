package fr.projet.diginamic.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Data transfer object of the user entity */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    /** The email of the user */
    private String email;

    /** The password of the user */
    private String password;
}
