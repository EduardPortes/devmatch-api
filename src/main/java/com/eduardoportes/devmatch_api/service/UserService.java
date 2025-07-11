package com.eduardoportes.devmatch_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import com.eduardoportes.devmatch_api.config.token.record.LoginRequest;
import com.eduardoportes.devmatch_api.config.token.record.RegisterRequest;
import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.model.User;
import com.eduardoportes.devmatch_api.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired  
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Boolean isLoginCorrect(LoginRequest loginRequest, User user){
        return bCryptPasswordEncoder.matches(loginRequest.password(), user.getPassword());
    }

    public User create(RegisterRequest registerRequest) {

        try {
            String pass = bCryptPasswordEncoder.encode(registerRequest.password());
            User newUser = User.builder()
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(pass)
                .build();
            
            return repository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }
    
    public User getUserByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Optional<User> userOpt = repository.findByEmail(jwt.getClaim("email"));
        return userOpt.orElse(null);
    }

}