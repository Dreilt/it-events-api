package pl.dreilt.iteventsapi.appuser.mapper;

import pl.dreilt.iteventsapi.appuser.dto.AppUserProfileEditDTO;
import pl.dreilt.iteventsapi.appuser.model.AppUser;

import java.time.format.DateTimeFormatter;

public class AppUserProfileEditDTOMapper {

    private AppUserProfileEditDTOMapper() {
    }

    public static AppUserProfileEditDTO mapToAppUserProfileEditDTO(AppUser user) {
        return AppUserProfileEditDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .city(user.getCity())
                .bio(user.getBio())
                .build();
    }
}
