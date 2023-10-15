package pl.dreilt.iteventsapi.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.dreilt.iteventsapi.appuser.model.AppUser;

public interface AppUserDetailsService extends UserDetailsService {

    void updateAppUserDetails(AppUser user);
}
