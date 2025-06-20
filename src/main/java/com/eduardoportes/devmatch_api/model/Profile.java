package com.eduardoportes.devmatch_api.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String name;
    private String bio;
    private String location;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "git_hub_url")
    private String gitHubUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToMany
    @JoinTable(
        name = "profile_technologies",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private List<Technology> technologies;

    @ManyToMany
    @JoinTable(
        name = "profile_interests",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private List<Interest> interests;

    @ElementCollection
    @CollectionTable(
            name = "profile_seeking_genders",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "seeking_gender")
    private List<Gender> seekingGenders;

}
