package com.eduardoportes.devmatch_api.repository;

import com.eduardoportes.devmatch_api.model.Gender;
import com.eduardoportes.devmatch_api.model.Match;
import com.eduardoportes.devmatch_api.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT m FROM Match m WHERE m.profile1.id = :userId OR m.profile2.id = :userId")
    List<Match> findAllUserMatches(Long userId);

}
