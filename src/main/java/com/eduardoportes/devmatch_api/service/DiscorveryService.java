package com.eduardoportes.devmatch_api.service;

import com.eduardoportes.devmatch_api.model.DisLike;
import com.eduardoportes.devmatch_api.model.Match;
import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.model.User;
import com.eduardoportes.devmatch_api.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DiscorveryService {

    @Autowired
    private UserService userService;

    @Autowired
    private LikeDislikeService likeDislikeService;

    @Autowired
    private ProfileRepository profileRepository;

    public Profile getPotentialMatches() {
        User user = userService.getUserByToken();
        Profile userProfile = user.getProfile();

        List<Long> dislikedIds = likeDislikeService
                .findDislikedProfiles(userProfile);

        if (dislikedIds.isEmpty()) {
            dislikedIds = Collections.singletonList(0L);
        }

        Pageable pageable = PageRequest.of(0, 10);

        Page<Profile> potentialProfiles = profileRepository.findPotentialMatches(
                userProfile.getGender(),
                userProfile.getSeekingGenders(),
                user.getId(),
                dislikedIds,
                pageable
        );
        List<Profile> shuffledProfiles = new ArrayList<>(potentialProfiles.getContent());
        Collections.shuffle(shuffledProfiles);

        return shuffledProfiles.isEmpty() ? null : shuffledProfiles.get(0);
    }
}
