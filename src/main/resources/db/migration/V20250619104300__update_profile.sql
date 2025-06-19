ALTER TABLE profiles
    ADD COLUMN gender VARCHAR(10) AFTER location;

CREATE TABLE profile_seeking_genders(
    id INT AUTO_INCREMENT PRIMARY KEY,
    profile_id BIGINT NOT NULL,
    seeking_gender VARCHAR(10) NOT NULL,
    CONSTRAINT FOREIGN KEY (profile_id) REFERENCES profiles(id)
);
