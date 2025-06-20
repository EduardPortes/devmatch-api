package com.eduardoportes.devmatch_api.resource;

import com.eduardoportes.devmatch_api.model.Match;
import com.eduardoportes.devmatch_api.model.request.MatchActionRequest;
import com.eduardoportes.devmatch_api.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchResource {

    @Autowired
    private MatchService matchService;

    @GetMapping()
    public ResponseEntity<List<Match>> getMatches() {
        List<Match> matches = matchService.getMatchesByUserAuthenticated();
        return ResponseEntity.ok().body(matches);
    }

    @PostMapping()
    public Boolean makeAction(@RequestBody MatchActionRequest request) {
        return matchService.makeAction(request.getProfileId(), request.getAction());
    }

}
