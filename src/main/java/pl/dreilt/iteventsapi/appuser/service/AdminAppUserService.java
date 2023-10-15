package pl.dreilt.iteventsapi.appuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.dreilt.iteventsapi.appuser.dto.*;
import pl.dreilt.iteventsapi.appuser.model.AppUser;

public interface AdminAppUserService {

    Page<AdminAppUserTableDTO> findAllUsers(Pageable page);

    Page<AdminAppUserTableDTO> findUsersBySearch(String searchQuery, Pageable page);

    AdminAppUserAccountEditDTO findUserAccountToEdit(Long id);

    AdminAppUserAccountEditDTO updateUserAccount(AppUser currentUser, Long id, AdminAppUserAccountEditDTO userAccount);

    AdminAppUserProfileEditDTO findUserProfileToEdit(Long id);

    AdminAppUserProfileEditDTO updateUserProfile(AppUser currentUser, Long id, AdminAppUserProfileEditDTO userProfile);

    void updateUserPassword(AppUser currentUser, Long id, AdminAppUserPasswordEditDTO newUserPassword);

    void deleteUser(AppUser currentUser, AdminDeleteAppUserDTO deleteUserData);
}
