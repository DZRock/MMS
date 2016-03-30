package ru.kubsu.mms.web.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Created by DZRock on 28.03.2016.
 */
@Service
public class DBAuthProvider implements AuthenticationProvider {

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    public boolean supports(Class<?> aClass) {
        return false;
    }
}
