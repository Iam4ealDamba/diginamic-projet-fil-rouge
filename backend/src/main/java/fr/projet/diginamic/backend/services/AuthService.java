package fr.projet.diginamic.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.LoginDto;
import fr.projet.diginamic.backend.dtos.RegisterDto;
import fr.projet.diginamic.backend.dtos.UserDto;
import fr.projet.diginamic.backend.entities.UserEntity;
import fr.projet.diginamic.backend.repositories.interfaces.UserRepository;
import fr.projet.diginamic.backend.utils.PasswordUtils;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    // Methods

    /**
     * Login with email and password
     * 
     * @param email    - the email of the user
     * @param password - the password of the user
     */
    public UserDto login(LoginDto loginDto) {
        Optional<UserEntity> user = userRepository.findByEmail(loginDto.getEmail());

        if (user.isEmpty()) {
            return null;
        }

        if (!PasswordUtils.checkPassword(loginDto.getPassword(), user.get().getPassword())) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.get().getId());
        userDto.setFirstName(user.get().getFirstName());
        userDto.setLastName(user.get().getLastName());
        userDto.setEmail(user.get().getEmail());
        userDto.setBirthDate(user.get().getBirthDate());
        return userDto;
    }

    /**
     * Register with email and password
     * 
     * @param register - the dto to register a new user
     */
    public UserDto register(RegisterDto register) {
        Optional<UserEntity> user = userRepository.findByEmail(register.getEmail());

        if (user.isPresent()) {
            return null;
        }

        String hash = PasswordUtils.hashPassword(register.getPassword());

        UserEntity newUser = new UserEntity();
        newUser.setFirstName(register.getFirstName());
        newUser.setLastName(register.getLastName());
        newUser.setBirthDate(register.getBirthDate());
        newUser.setEmail(register.getEmail());
        newUser.setPassword(hash);
        userRepository.save(newUser);

        UserDto userDto = new UserDto();
        userDto.setId(newUser.getId());
        userDto.setFirstName(newUser.getFirstName());
        userDto.setLastName(newUser.getLastName());
        userDto.setBirthDate(newUser.getBirthDate());
        userDto.setEmail(newUser.getEmail());
        return userDto;
    }
}
