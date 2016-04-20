package ru.kubsu.mms.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kubsu.mms.core.db.models.Role;
import ru.kubsu.mms.core.db.models.User;
import ru.kubsu.mms.core.db.repo.UserRepo;
import ru.kubsu.mms.web.dto.LoginDto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DZRock on 29.03.2016.
 */
@Component
public class CustomUserDetailsService implements AuthenticationProvider {

    @Autowired
    private UserRepo userRepo;

    private final String saPswd = "c62d929e7b7e7b6165923a5dfc60cb56";

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginDto loginDto = new LoginDto((String)authentication.getPrincipal(), (String)authentication.getCredentials());

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(loginDto.getPassword().getBytes("utf-8"));
        } catch (Exception e) {
            throw new AuthenticationServiceException("Error calculating passwd hash");
        }
        String passwordHash = new BigInteger(1, md5.digest()).toString(16);
        if (loginDto.getEmail().equals("sa")) return buildSaSession(loginDto, passwordHash);

        User user = userRepo.getByEmailAndPassword(loginDto.getEmail(), passwordHash);
        if (user == null)
            throw new UsernameNotFoundException("User " + loginDto.getEmail() + " is not found.");

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new UsernamePasswordAuthenticationToken(user, loginDto, roles);
    }

    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

    //Method for build SA user and validate SA password
    private Authentication buildSaSession(LoginDto loginDto, String passwordHash) {
        if (!passwordHash.equals(saPswd))
            throw new UsernameNotFoundException("User " + loginDto.getEmail() + " is not found.");

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        User user = new User();
        user.setName("Главный администратор");

        return new UsernamePasswordAuthenticationToken(user, loginDto, roles);
    }
}
