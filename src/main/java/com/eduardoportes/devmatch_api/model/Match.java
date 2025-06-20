package com.eduardoportes.devmatch_api.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile1_id")
    private Profile profile1;

    @ManyToOne
    @JoinColumn(name = "profile2_id")
    private Profile profile2;

}
