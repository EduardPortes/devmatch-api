package com.eduardoportes.devmatch_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.repository.custom.ProfileRepositoryCustom;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileRepositoryCustom{
    

}
