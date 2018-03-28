package fr.istic.sit.codisgroupea.service;

import fr.istic.sit.codisgroupea.model.entity.User;
import fr.istic.sit.codisgroupea.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserWithPassword(String userlogin, String password){
        Optional<User> user = userRepository.findByUsername(userlogin);

        if (!user.isPresent()){
            return Optional.empty();
        }

        if (password.equals(user.get().getPassword())) {
            return user;
        }else{
            return Optional.empty();
        }
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
