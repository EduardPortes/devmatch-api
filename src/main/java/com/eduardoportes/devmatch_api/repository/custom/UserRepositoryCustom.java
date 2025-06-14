package com.eduardoportes.devmatch_api.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eduardoportes.devmatch_api.model.User;
import com.eduardoportes.devmatch_api.repository.filter.UserFilter;

public interface UserRepositoryCustom {

    Page<User> filter(UserFilter filter ,Pageable pageable);
}
