package com.eduardoportes.devmatch_api.repository;

import com.eduardoportes.devmatch_api.model.Gender;
import com.eduardoportes.devmatch_api.repository.filter.ProfileFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.repository.custom.ProfileRepositoryCustom;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileRepositoryCustom{

    Page<Profile> filter(ProfileFilter filter, Pageable pageable);

    @Query("SELECT p FROM Profile p " +
            "WHERE p.gender IN :seekingGenders " +
            "AND :userGender MEMBER OF p.seekingGenders " +
            "AND p.user.id <> :excludedUserId")
    Page<Profile> findPotentialMatches(
            @Param("userGender") Gender userGender,
            @Param("seekingGenders") List<Gender> seekingGenders,
            @Param("excludedUserId") Long excludedUserId,
            Pageable pageable
    );

}
