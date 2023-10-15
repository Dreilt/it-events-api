package pl.dreilt.iteventsapi.profileimage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dreilt.iteventsapi.profileimage.model.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
