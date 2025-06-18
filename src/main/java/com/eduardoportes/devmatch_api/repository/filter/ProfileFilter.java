package com.eduardoportes.devmatch_api.repository.filter;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileFilter {

    private LocalDate birthDateBegin;
    private LocalDate birthDateEnd;

}
