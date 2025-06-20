package com.eduardoportes.devmatch_api.service;

import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.model.User;
import com.eduardoportes.devmatch_api.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscorveryService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileRepository profileRepository;

    public Profile getPotentialMatches() {
        User user = userService.getUserByToken();
        List<Profile> listPotential = profileRepository.findPotentialMatches(
                user.getProfile().getGender(),
                user.getProfile().getSeekingGenders(),
                user.getId(), null).getContent();
        return listPotential.getFirst();
    }
}
