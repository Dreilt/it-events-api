package pl.dreilt.iteventsapi.appuser.mapper;

import org.springframework.data.domain.Page;
import pl.dreilt.iteventsapi.appuser.dto.AppUserTableDTO;
import pl.dreilt.iteventsapi.appuser.model.AppUser;

public class AppUserTableDTOMapper {

    private AppUserTableDTOMapper() {
    }

    public static Page<AppUserTableDTO> mapToAppUserTableDTOs(Page<AppUser> users) {
        return users.map(AppUserTableDTOMapper::mapToAppUserTableDTO);
    }

    private static AppUserTableDTO mapToAppUserTableDTO(AppUser user) {
        return AppUserTableDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
