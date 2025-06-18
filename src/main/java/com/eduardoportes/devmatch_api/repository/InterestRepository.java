package com.eduardoportes.devmatch_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduardoportes.devmatch_api.model.Interest;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long>{
    
}
