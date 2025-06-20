package com.eduardoportes.devmatch_api.repository;

import com.eduardoportes.devmatch_api.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    
    Like findByLikerProfileIdAndLikedProfileId(Long likerProfileId, Long likedProfileId);

}
