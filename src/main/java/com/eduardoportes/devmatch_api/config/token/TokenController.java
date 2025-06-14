package com.eduardoportes.devmatch_api.config.token;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardoportes.devmatch_api.config.token.record.LoginRequest;
import com.eduardoportes.devmatch_api.config.token.record.LoginResponse;
import com.eduardoportes.devmatch_api.config.token.record.RegisterRequest;
import com.eduardoportes.devmatch_api.model.User;
import com.eduardoportes.devmatch_api.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class TokenController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserService userService;


    @Value("${jwt.expiration}")
    private Long expires_in;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var userOpt = userService.findByEmail(loginRequest.email());

        if(!userOpt.isPresent() || !userService.isLoginCorrect(loginRequest, userOpt.get())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userOpt.get();
        try {
            String token = createToken(user);
            return ResponseEntity.ok().body(new LoginResponse(token, expires_in));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest registerRequest) {

        if(userService.findByEmail(registerRequest.email()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if(userService.findByUsername(registerRequest.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        try {
            var newUser = userService.create(registerRequest);
            String token = createToken(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(token, expires_in));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String createToken(User user) {
        var now = Instant.now();
        
        var claims = JwtClaimsSet.builder()
            .subject(user.getId().toString())
            .claim("username", user.getUsername())
            .claim("email", user.getEmail())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expires_in))
            .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
