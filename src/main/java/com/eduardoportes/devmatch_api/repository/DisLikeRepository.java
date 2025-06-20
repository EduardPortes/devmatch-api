package com.eduardoportes.devmatch_api.repository;

import com.eduardoportes.devmatch_api.model.DisLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisLikeRepository extends JpaRepository<DisLike,Long> {

    @Query("SELECT d.dislikedProfile.id FROM DisLike d WHERE d.dislikerProfile.id = :dislikerProfileId")
    List<Long> findDislikedProfileIdsByDislikerProfileId(@Param("dislikerProfileId") Long dislikerProfileId);

}
