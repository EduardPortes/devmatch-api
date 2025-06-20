package com.eduardoportes.devmatch_api.resource;


import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.service.DiscorveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discovery")
public class DiscorveryResource {

    @Autowired
    private DiscorveryService discorveryService;

    @GetMapping("/potential-matches")
    public ResponseEntity<Profile> getPotentionalMatches() {
        Profile profile = discorveryService.getPotentialMatches();
        return ResponseEntity.ok().body(profile);
    }



}
