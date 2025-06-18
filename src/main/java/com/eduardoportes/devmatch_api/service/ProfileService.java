package com.eduardoportes.devmatch_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardoportes.devmatch_api.model.Interest;
import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.model.Technology;
import com.eduardoportes.devmatch_api.model.User;
import com.eduardoportes.devmatch_api.repository.InterestRepository;
import com.eduardoportes.devmatch_api.repository.ProfileRepository;
import com.eduardoportes.devmatch_api.repository.TecnologyRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private TecnologyRepository technologyRepository;

    @Autowired
    private UserService userService;

    public List<Interest> getAllInterests(){
        List<Interest> interests = interestRepository.findAll();
        return interests;
    }

    public List<Technology>getAllTechnologies(){
        List<Technology> technologies = technologyRepository.findAll();
        return technologies;
    }

    public Profile create(Profile profile) {
        User user = userService.getUserByToken();
        profile.setUser(user);
        return profileRepository.save(profile);
    }

    public Profile update(Profile profile) {
        Long idProfile = userService.getUserByToken().getProfile().getId();

        return profileRepository.findById(idProfile)
            .map(existingProfile -> {
                existingProfile.setBio(profile.getBio());
                existingProfile.setTechnologies(profile.getTechnologies());
                existingProfile.setInterests(profile.getInterests());
                existingProfile.setLocation(profile.getLocation());

                return profileRepository.save(existingProfile);
            })
            .orElseThrow();
    }

}
