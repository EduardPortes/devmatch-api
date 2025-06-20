package com.eduardoportes.devmatch_api.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "liker_profile_id")
    private Profile likerProfile;

    @ManyToOne
    @JoinColumn(name = "liked_profile_id")
    private Profile likedProfile;

}
