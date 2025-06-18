package com.eduardoportes.devmatch_api.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduardoportes.devmatch_api.model.Interest;
import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.model.Technology;
import com.eduardoportes.devmatch_api.service.ProfileService;
import com.eduardoportes.devmatch_api.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profiles")
public class ProfileResource {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @GetMapping("/interests")
    public ResponseEntity<List<Interest>> getAllInterests(){
        List<Interest> interests = profileService.getAllInterests();
        return ResponseEntity.ok().body(interests);
    }

    @GetMapping("/technologies")
    public ResponseEntity<List<Technology>> getAllTechnologies(){
        List<Technology> technologies = profileService.getAllTechnologies();
        return ResponseEntity.ok().body(technologies);
    }

    @PostMapping()
    public ResponseEntity<Profile> create(@RequestBody Profile profile) {
        Profile newProfile = profileService.create(profile);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newProfile.getId())
            .toUri();
    return ResponseEntity.created(location).body(newProfile);
    }

    @PutMapping("/me")
    public ResponseEntity<Profile> update(@RequestBody Profile profile) {
        Profile newProfile = profileService.update(profile);
        return ResponseEntity.ok().body(newProfile);
    }

}