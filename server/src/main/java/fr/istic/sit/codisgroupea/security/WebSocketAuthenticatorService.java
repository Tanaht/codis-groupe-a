package fr.istic.sit.codisgroupea.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class WebSocketAuthenticatorService {
    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  username, final String password) throws AuthenticationException {
        if (username == null || username.trim().length() == 0) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }
        // Add your own logic for retrieving user in fetchUserFromDb()
        if (!correctCredential(username, password)){
            throw new BadCredentialsException("Bad credentials for user " + username);
        }

        // null credentials, we do not pass the password along to prevent security flaw
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singleton((GrantedAuthority) () -> "USER")
        );
    }

    private boolean correctCredential(String username, String password) {
        System.out.println("correct credentials :" + username + ", " + password);
        return true;
    }

}
