package pl.dreilt.iteventsapi.profileimage.service;

import org.springframework.web.multipart.MultipartFile;
import pl.dreilt.iteventsapi.appuser.model.AppUser;
import pl.dreilt.iteventsapi.profileimage.model.ProfileImage;

import java.util.Optional;

public interface ProfileImageService {

    ProfileImage createDefaultProfileImage();

    Optional<ProfileImage> updateProfileImage(AppUser user, MultipartFile newProfileImage);
}
