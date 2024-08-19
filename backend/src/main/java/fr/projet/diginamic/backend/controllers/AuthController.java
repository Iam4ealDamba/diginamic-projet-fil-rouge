package fr.projet.diginamic.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.diginamic.backend.dtos.LoginDto;
import fr.projet.diginamic.backend.dtos.RegisterDto;
import fr.projet.diginamic.backend.dtos.UserDto;
import fr.projet.diginamic.backend.services.AuthService;

/** Auth controller for users */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * Route for login with email and password
     * 
     * @param loginDto - the dto to login
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String userDto = authService.login(loginDto);

        if (userDto == null) {
            return new ResponseEntity<>(new UsernameNotFoundException("Email/Password not valid").getMessage(),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userDto);
    }

    /**
     * Route for registering a new user
     * 
     * @param registerDto - the dto to register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto) {
        UserDto userDto = authService.register(registerDto);

        if (userDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userDto);
    }

    /** Route for retrieve user token */
    @GetMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        if (token == null || token.substring(7).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(token);
    }

    /** Route for check user authorization */
    @GetMapping("/user")
    public ResponseEntity<String> checkUser() {
        return ResponseEntity.ok("OK");
    }

    /** Route for check manager authorization */
    @GetMapping("/manager")
    public ResponseEntity<String> checkManager() {
        return ResponseEntity.ok("OK");
    }

    /** Route for check admin authorization */
    @GetMapping("/admin")
    public ResponseEntity<String> checkAdmin() {
        return ResponseEntity.ok("OK");
    }

}
