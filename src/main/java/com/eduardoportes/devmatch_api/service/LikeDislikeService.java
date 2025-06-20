package com.eduardoportes.devmatch_api.service;

import com.eduardoportes.devmatch_api.model.DisLike;
import com.eduardoportes.devmatch_api.model.Like;
import com.eduardoportes.devmatch_api.model.Match;
import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.repository.DisLikeRepository;
import com.eduardoportes.devmatch_api.repository.LikeRepository;
import com.eduardoportes.devmatch_api.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeDislikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private DisLikeRepository disLikeRepository;

    @Autowired
    private MatchRepository matchRepository;

    public boolean like(Profile profile, Profile targetProfile) {
        Like like = Like.builder()
                .likerProfile(profile)
                .likedProfile(targetProfile)
                .build();
        likeRepository.save(like);

        Like existingLike = likeRepository.findByLikerProfileIdAndLikedProfileId(targetProfile.getId(), profile.getId());
        if(existingLike != null) {
            Match newMatch = Match.builder()
                    .profile1(profile)
                    .profile2(targetProfile)
                    .build();
            matchRepository.save(newMatch);
            return true;
        }
        return false;
    }

    public void dislike(Profile profile, Profile targetProfile) {
        DisLike disLike = DisLike.builder()
                .dislikerProfile(profile)
                .dislikedProfile(targetProfile)
                .build();
        disLikeRepository.save(disLike);

        Like existingLike = likeRepository.findByLikerProfileIdAndLikedProfileId(profile.getId(), targetProfile.getId());
        if (existingLike != null) {
            likeRepository.delete(existingLike);
        }
    }

    public List<Long> findDislikedProfiles(Profile profile) {
        return disLikeRepository.findDislikedProfileIdsByDislikerProfileId(profile.getId());
    }

}
