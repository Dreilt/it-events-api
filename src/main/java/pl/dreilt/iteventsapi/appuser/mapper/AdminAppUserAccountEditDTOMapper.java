package pl.dreilt.iteventsapi.appuser.mapper;

import pl.dreilt.iteventsapi.appuser.dto.AdminAppUserAccountEditDTO;
import pl.dreilt.iteventsapi.appuser.model.AppUser;

public class AdminAppUserAccountEditDTOMapper {

    private AdminAppUserAccountEditDTOMapper() {
    }

    public static AdminAppUserAccountEditDTO mapToAdminAppUserAccountEditDTO(AppUser user) {
        return AdminAppUserAccountEditDTO.builder()
                .id(user.getId())
                .enabled(user.isEnabled())
                .accountNonLocked(user.isAccountNonLocked())
                .roles(user.getRoles())
                .build();
    }
}
