package fr.projet.diginamic.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.LoginDto;
import fr.projet.diginamic.backend.dtos.RegisterDto;
import fr.projet.diginamic.backend.dtos.user.UserDto;
import fr.projet.diginamic.backend.dtos.user.UserPasswordDto;
import fr.projet.diginamic.backend.entities.Role;
import fr.projet.diginamic.backend.entities.UserEntity;
import fr.projet.diginamic.backend.repositories.interfaces.UserRepository;

/** Service class for managing authentication */
@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;

    // Methods

    /**
     * Login with email and password
     * 
     * @param loginDto - the dto to login
     */
    public String login(LoginDto loginDto) {
        Optional<UserEntity> user = userRepository.findByEmail(loginDto.getEmail());

        if (user.isEmpty()) {
            return null;
        }

        Authentication authResult = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        if (!authResult.isAuthenticated()) {
            return null;
        } else {
            return jwtService.generateToken(userService.loadUserByUsername(user.get().getEmail()),
                    user.get().getRole().getType());
        }
    }

    /**
     * Register with email and password
     * 
     * @param register - the dto to register a new user
     */
    public String register(RegisterDto register) {
        Optional<UserEntity> user = userRepository.findByEmail(register.getEmail());

        if (user.isPresent()) {
            return null;
        }

        String hash = passwordEncoder.encode(register.getPassword());
        Role user_role = new Role();

        if (register.getRole() == null) {
            user_role.setId(3);
            user_role.setType("USER");
        } else {
            switch (register.getRole()) {
                case "ADMIN" -> {
                    user_role.setId(1);
                    user_role.setType("ADMIN");
                }
                case "MANAGER" -> {
                    user_role.setId(2);
                    user_role.setType("MANAGER");
                }
                default -> {
                    user_role.setId(3);
                    user_role.setType("USER");
                }
            }
        }

        UserEntity newUser = new UserEntity();
        newUser.setFirstName(register.getFirstName());
        newUser.setLastName(register.getLastName());
        newUser.setBirthDate(register.getBirthDate());
        newUser.setEmail(register.getEmail());
        newUser.setPassword(hash);
        newUser.setRole(user_role);
        userRepository.save(newUser);

        return jwtService.generateToken(userService.loadUserByUsername(newUser.getEmail()),
                newUser.getRole().getType());
    }

    /**
     * Get the current user with the access token
     * 
     * @param token - the token
     */
    public UserDto getCurrentUser(String token) {
        String email = jwtService.extractUsername(token);
        UserEntity user = userService.getOneByEmail(email);

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getEmail(),
                user.getRole().getType());
    }

    /**
     * Refresh token by generating a new JWT
     * 
     * @param oldToken - the token to refresh
     */
    public String refreshToken(String oldToken) {
        Boolean isTokenValid = jwtService.isTokenExpirationValid(oldToken);
        if (!isTokenValid) {
            return null;
        }
        return jwtService.refreshToken(oldToken);
    }

    /**
     * Update one user
     * 
     * @param id      - the id of the user
     * @param userDto - the dto of the user
     */
    public UserEntity updatePassword(Long id, UserPasswordDto userPasswordDto) {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty() || userPasswordDto.getOldPassword() == null) {
            return null;
        }

        UserEntity newUser = user.get();

        if (!newUser.getPassword().equals(userPasswordDto.getOldPassword())) {
            return null;
        }

        String hash = passwordEncoder.encode(userPasswordDto.getNewPassword());

        newUser.setPassword(hash);
        userRepository.save(newUser);

        return newUser;
    }

}