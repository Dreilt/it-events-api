package pl.dreilt.iteventsapi.creator;

import pl.dreilt.iteventsapi.appuser.dto.AppUserProfileEditDTO;
import pl.dreilt.iteventsapi.appuser.model.AppUser;

import java.time.format.DateTimeFormatter;

public class AppUserProfileEditDTOCreator {

    public static AppUserProfileEditDTO create(AppUser user, String city, String bio) {
        return AppUserProfileEditDTO.builder()
//                .profileImage(ProfileImageCreator.createNewProfileImageFile())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .city(city)
                .bio(bio)
                .build();
    }
}
