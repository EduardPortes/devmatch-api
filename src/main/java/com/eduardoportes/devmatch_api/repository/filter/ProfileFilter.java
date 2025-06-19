package com.eduardoportes.devmatch_api.repository.filter;

import java.time.LocalDate;
import java.util.List;

import com.eduardoportes.devmatch_api.model.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileFilter {

    private LocalDate birthDateBegin;
    private LocalDate birthDateEnd;

}
