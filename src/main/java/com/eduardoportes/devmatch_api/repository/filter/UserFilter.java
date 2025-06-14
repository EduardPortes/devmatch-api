package com.eduardoportes.devmatch_api.repository.filter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilter {

    // User
    private String username;
    private String email;

    // Profile
    private String name;
    private String bio;
    private String location;
    private String gitHubUrl;
    private String linkedinUrl;

    // Related    
    private List<String> technologies;
    private List<String> interests;

    // Age
    private Integer minAge;
    private Integer maxAge;

}
