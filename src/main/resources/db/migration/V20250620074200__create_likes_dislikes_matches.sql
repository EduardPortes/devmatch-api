CREATE TABLE likes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    liker_profile_id BIGINT NOT NULL,
    liked_profile_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (liker_profile_id) REFERENCES profiles(id),
    FOREIGN KEY (liked_profile_id) REFERENCES profiles(id),
    UNIQUE KEY unique_like (liker_profile_id, liked_profile_id)
);

CREATE TABLE dislikes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    disliker_profile_id BIGINT NOT NULL,
    disliked_profile_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (disliker_profile_id) REFERENCES profiles(id),
    FOREIGN KEY (disliked_profile_id) REFERENCES profiles(id),
    UNIQUE KEY unique_dislike (disliker_profile_id, disliked_profile_id)
);

CREATE TABLE matches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profile1_id BIGINT NOT NULL,
    profile2_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (profile1_id) REFERENCES profiles(id),
    FOREIGN KEY (profile2_id) REFERENCES profiles(id),
    UNIQUE KEY unique_match (profile1_id, profile2_id)
);