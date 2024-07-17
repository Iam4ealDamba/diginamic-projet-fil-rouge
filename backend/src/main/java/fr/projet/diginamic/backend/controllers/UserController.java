package fr.projet.diginamic.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.diginamic.backend.dtos.UserDto;
import fr.projet.diginamic.backend.entities.UserEntity;
import fr.projet.diginamic.backend.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> all() {
        List<UserEntity> users = userService.getAll();
        List<UserDto> usersDto = new ArrayList<>();

        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        for (UserEntity user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setBirthDate(user.getBirthDate());
            userDto.setEmail(user.getEmail());

            usersDto.add(userDto);
        }

        return ResponseEntity.ok(usersDto);
    }

    @GetMapping
    public ResponseEntity<UserDto> one(@PathVariable String id) {
        UserEntity user = userService.getOne(Integer.parseInt(id));

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setEmail(user.getEmail());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> update(@PathVariable Integer id, @RequestBody UserDto userDto) {
        List<UserEntity> users = userService.update(id, userDto);
        List<UserDto> usersDto = new ArrayList<>();

        if (users == null) {
            return ResponseEntity.notFound().build();
        }

        for (UserEntity user : users) {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setBirthDate(user.getBirthDate());
            dto.setEmail(user.getEmail());
            usersDto.add(dto);
        }

        return ResponseEntity.ok(usersDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> delete(@PathVariable String id) {
        List<UserEntity> users = userService.delete(Integer.parseInt(id));
        List<UserDto> usersDto = new ArrayList<>();

        if (users == null) {
            return ResponseEntity.notFound().build();
        }

        for (UserEntity user : users) {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setBirthDate(user.getBirthDate());
            dto.setEmail(user.getEmail());
            usersDto.add(dto);
        }
        return ResponseEntity.ok(usersDto);
    }
}
