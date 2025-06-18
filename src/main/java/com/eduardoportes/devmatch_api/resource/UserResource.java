package com.eduardoportes.devmatch_api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.service.UserService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<Profile> getCurrentUserProfile() {
        Profile profile = userService.getUserByToken().getProfile();
        return ResponseEntity.ok().body(profile);
    }
}
