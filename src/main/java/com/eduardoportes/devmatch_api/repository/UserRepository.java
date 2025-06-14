package com.eduardoportes.devmatch_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduardoportes.devmatch_api.model.User;
import com.eduardoportes.devmatch_api.repository.custom.UserRepositoryCustom;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom{

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
