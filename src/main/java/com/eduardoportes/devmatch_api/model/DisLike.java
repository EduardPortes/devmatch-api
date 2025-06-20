package com.eduardoportes.devmatch_api.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dislikes")
public class DisLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "disliker_profile_id")
    private Profile dislikerProfile;

    @ManyToOne
    @JoinColumn(name = "disliked_profile_id")
    private Profile dislikedProfile;

}
