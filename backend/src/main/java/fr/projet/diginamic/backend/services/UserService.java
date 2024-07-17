package fr.projet.diginamic.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.UserDto;
import fr.projet.diginamic.backend.entities.UserEntity;
import fr.projet.diginamic.backend.repositories.interfaces.UserRepository;
import fr.projet.diginamic.backend.services.interfaces.ServiceInterface;

@Service
public class UserService implements ServiceInterface<UserEntity, UserDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> users = userRepository.findAll();

        if (users.isEmpty()) {
            return null;
        }
        return users;
    }

    @Override
    public UserEntity getOne(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    @Override
    public List<UserEntity> update(Long id, UserDto userDto) {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return null;
        }

        UserEntity newUser = user.get();
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setBirthDate(userDto.getBirthDate());
        newUser.setEmail(userDto.getEmail());
        userRepository.save(newUser);

        return this.getAll();
    }

    @Override
    public List<UserEntity> delete(Long id) {
        List<UserEntity> users = userRepository.findAll();
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return null;
        }

        userRepository.delete(user.get());
        return this.getAll();
    }

    @Override
    public List<UserEntity> create(UserDto userDto) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

}
