package com.eduardoportes.devmatch_api.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.repository.filter.ProfileFilter;

public interface ProfileRepositoryCustom {

    Page<Profile> filter(ProfileFilter filter, Pageable pageable);

}
