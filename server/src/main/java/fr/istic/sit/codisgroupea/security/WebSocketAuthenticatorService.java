package fr.istic.sit.codisgroupea.security;

import fr.istic.sit.codisgroupea.model.entity.Role;
import fr.istic.sit.codisgroupea.model.entity.User;
import fr.istic.sit.codisgroupea.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class WebSocketAuthenticatorService {


    private AuthenticationService authenticationService;

    public WebSocketAuthenticatorService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  username, final String password) {
        if (username == null || username.trim().length() == 0) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }

        Optional<User> user = authenticationService.getUserWithPassword(username,password);

        // Add your own logic for retrieving user in fetchUserFromDb()
        if (!user.isPresent()){
            throw new BadCredentialsException("Bad credentials for user " + username);
        }


        // null credentials, we do not pass the password along to prevent security flaw
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                new ArrayList<>()
        );
    }
}
