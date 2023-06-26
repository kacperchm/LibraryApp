/*
package com.kacperchm.librarybackend.security;

import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class MyAuthenticationProvider implements AuthenticationProvider {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    public MyAuthenticationProvider(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<User> user;
        String login = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        if(usersRepository.existsByUsername(login)) {
            user = usersRepository.findByUsername(login);
        }else {
            user = usersRepository.findByMail(login);
        }
        if (user.size() > 0) {
            if(passwordEncoder.matches(pwd,user.get(0).getPassword())) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.get(0).getRole()));
                return new UsernamePasswordAuthenticationToken(login,pwd,authorities);
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            throw new BadCredentialsException("No user registered with this details");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
*/
