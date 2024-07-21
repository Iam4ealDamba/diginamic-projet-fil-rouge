package fr.projet.diginamic.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.UserDto;
import fr.projet.diginamic.backend.entities.UserEntity;
import fr.projet.diginamic.backend.repositories.interfaces.UserRepository;
import fr.projet.diginamic.backend.services.interfaces.ServiceInterface;

/** User service */
@Service
public class UserService implements ServiceInterface<UserEntity, UserDto>, UserDetailsService {

    /** User repository */
    @Autowired
    private UserRepository userRepository;

    /** Get all users */
    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    /**
     * Get one user
     * 
     * @param id - the id of the user
     */
    @Override
    public UserEntity getOne(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    /**
     * Update one user
     * 
     * @param id      - the id of the user
     * @param userDto - the dto of the user
     */
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

    /**
     * Delete one user
     * 
     * @param id - the id of the user
     * 
     * @return the list of users
     */
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

    /**
     * Load user detail from email
     * 
     * @param email - the email of the user
     * 
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            return User.builder().username(userEntity.getEmail()).password(userEntity.getPassword())
                    .roles(userEntity.getRole().getType())
                    .build();
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

}
