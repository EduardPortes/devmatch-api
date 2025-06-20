package com.eduardoportes.devmatch_api.service;

import com.eduardoportes.devmatch_api.model.Match;
import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.model.User;
import com.eduardoportes.devmatch_api.repository.MatchRepository;
import com.eduardoportes.devmatch_api.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private LikeDislikeService likeDislikeService;

    @Autowired
    private UserService userService;

    public List<Match> getMatchesByUserAuthenticated() {
        User user = userService.getUserByToken();
        return matchRepository.findAllUserMatches(user.getId());
    }

    public boolean makeAction(Long profileId, boolean action) {
        User user = userService.getUserByToken();
        Profile targetProfile = profileRepository.findById(profileId).get();

        if (action) {
            return likeDislikeService.like(user.getProfile(), targetProfile);
        } else {
            likeDislikeService.dislike(user.getProfile(), targetProfile);
            return false;
        }

    }


}
