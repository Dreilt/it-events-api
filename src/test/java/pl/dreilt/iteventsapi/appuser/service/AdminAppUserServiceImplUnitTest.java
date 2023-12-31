package pl.dreilt.iteventsapi.appuser.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dreilt.iteventsapi.appuser.dto.*;
import pl.dreilt.iteventsapi.appuser.exception.AppUserNotFoundException;
import pl.dreilt.iteventsapi.appuser.exception.IncorrectCurrentPasswordException;
import pl.dreilt.iteventsapi.appuser.model.AppUser;
import pl.dreilt.iteventsapi.appuser.repository.AppUserRepository;
import pl.dreilt.iteventsapi.creator.AdminAppUserProfileEditDTOCreator;
import pl.dreilt.iteventsapi.creator.AppUserCreator;
import pl.dreilt.iteventsapi.profileimage.service.ProfileImageService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static pl.dreilt.iteventsapi.appuser.model.Role.ROLE_ADMIN;

class AdminAppUserServiceImplUnitTest {
    static final PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.fromString("ASC"), "lastName"));
    private AppUserRepository appUserRepository;
    private ProfileImageService profileImageService;
    private PasswordEncoder passwordEncoder;
    private AdminAppUserServiceImpl adminAppUserServiceImpl;

    @BeforeEach
    void setUp() {
        appUserRepository = Mockito.mock(AppUserRepository.class);
        profileImageService = Mockito.mock(ProfileImageService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        adminAppUserServiceImpl = new AdminAppUserServiceImpl(
                appUserRepository,
                profileImageService,
                passwordEncoder
        );
    }

    @Test
    void shouldReturnAllUsers() {
        // given
        List<AppUser> users = List.of(
                AppUserCreator.create(1L, "Admin", "Admin", ROLE_ADMIN),
                AppUserCreator.create(2L, "Jan", "Kowalski"),
                AppUserCreator.create(3L, "Patryk", "Kowalski")
        );
        when(appUserRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(users, pageRequest, users.size()));
        // when
        Page<AdminAppUserTableDTO> returnedUsers = adminAppUserServiceImpl.findAllUsers(pageRequest);
        // then
        assertThat(returnedUsers).isNotEmpty();
        assertThat(returnedUsers).hasSize(3);
        assertThat(returnedUsers.getContent().get(0).getLastName()).isEqualTo("Admin");
        assertThat(returnedUsers.getContent().get(1).getLastName()).isEqualTo("Kowalski");
        assertThat(returnedUsers.getContent().get(2).getLastName()).isEqualTo("Kowalski");
    }

    @Test
    void shouldReturnEmptyPageOfUsersIfSearchQueryIsEmpty() {
        // given
        String searchQuery = "";
        // when
        Page<AdminAppUserTableDTO> returnedUsers = adminAppUserServiceImpl.findUsersBySearch(searchQuery, pageRequest);
        // then
        assertThat(returnedUsers).isEmpty();
    }

    @Test
    void shouldReturnUsersBySearchIfSearchQueryHasOneWord() {
        // given
        List<AppUser> users = List.of(
                AppUserCreator.create(2L, "Jan", "Kowalski"),
                AppUserCreator.create(3L, "Patryk", "Kowalski")
        );
        when(appUserRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(new PageImpl<>(users, pageRequest, users.size()));
        // when
        Page<AdminAppUserTableDTO> returnedUsers = adminAppUserServiceImpl.findUsersBySearch("kowalski", pageRequest);
        // then
        assertThat(returnedUsers).isNotEmpty();
        assertThat(returnedUsers).hasSize(2);
        assertThat(returnedUsers.getContent().get(0).getLastName()).isEqualTo("Kowalski");
        assertThat(returnedUsers.getContent().get(1).getLastName()).isEqualTo("Kowalski");
    }

    @Test
    void shouldReturnUsersBySearchIfSearchQueryHasTwoWord() {
        // given
        List<AppUser> users = List.of(
                AppUserCreator.create(2L, "Jan", "Kowalski")
        );
        when(appUserRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(new PageImpl<>(users, pageRequest, users.size()));
        // when
        Page<AdminAppUserTableDTO> returnedUsers = adminAppUserServiceImpl.findUsersBySearch("jan kowalski", pageRequest);
        // then
        assertThat(returnedUsers).isNotEmpty();
        assertThat(returnedUsers).hasSize(1);
        assertThat(returnedUsers.getContent().get(0).getFirstName()).isEqualTo("Jan");
        assertThat(returnedUsers.getContent().get(0).getLastName()).isEqualTo("Kowalski");
    }

    @Test
    void shouldReturnUserAccountToEdit() {
        // given
        AppUser user = AppUserCreator.create(2L, "Jan", "Kowalski");
        when(appUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        // when
        AdminAppUserAccountEditDTO returnedUserAccountToEdit = adminAppUserServiceImpl.findUserAccountToEdit(user.getId());
        // then
        assertThat(returnedUserAccountToEdit).isNotNull();
        assertThat(returnedUserAccountToEdit.isEnabled()).isTrue();
        assertThat(returnedUserAccountToEdit.isAccountNonLocked()).isTrue();
        assertThat(returnedUserAccountToEdit.getRoles().size()).isEqualTo(1);
        assertThat(returnedUserAccountToEdit.getRoles().get(0)).isEqualTo(ROLE_USER);
    }

    @Test
    void shouldReturnUpdatedUserAccount() {
        // given
        AppUser admin = AppUserCreator.create(1L, "Admin", "Admin", ROLE_ADMIN);
        AppUser user = AppUserCreator.create(2L, "Jan", "Kowalski");
        when(appUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        AdminAppUserAccountEditDTO newUserAccountData = AdminAppUserAccountEditDTO.builder()
                .id(user.getId())
                .enabled(false)
                .accountNonLocked(false)
                .roles(List.of(ROLE_ORGANIZER))
                .build();
        // when
        AdminAppUserAccountEditDTO updatedUserAccount = adminAppUserServiceImpl.updateUserAccount(admin, user.getId(), newUserAccountData);
        // then
        assertThat(updatedUserAccount).isNotNull();
        assertThat(updatedUserAccount.isEnabled()).isFalse();
        assertThat(updatedUserAccount.isAccountNonLocked()).isFalse();
        assertThat(updatedUserAccount.getRoles().size()).isEqualTo(1);
        assertThat(updatedUserAccount.getRoles().get(0)).isEqualTo(ROLE_ORGANIZER);
    }

    @Test
    void shouldReturnUserProfileToEdit() {
        // given
        AppUser user = AppUserCreator.create(2L, "Jan", "Kowalski");
        when(appUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        // when
        AdminAppUserProfileEditDTO returnedUserProfileToEdit = adminAppUserServiceImpl.findUserProfileToEdit(user.getId());
        // then
        assertThat(returnedUserProfileToEdit).isNotNull();
        assertThat(returnedUserProfileToEdit.getProfileImage()).isNull();
        assertThat(returnedUserProfileToEdit.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(returnedUserProfileToEdit.getLastName()).isEqualTo(user.getLastName());
        assertThat(returnedUserProfileToEdit.getDateOfBirth()).isEqualTo(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(returnedUserProfileToEdit.getCity()).isNull();
        assertThat(returnedUserProfileToEdit.getBio()).isNull();
    }

    @Test
    void shouldReturnUpdatedUserProfile() {
        // given
        AppUser admin = AppUserCreator.create(1L, "Admin", "Admin", ROLE_ADMIN);
        AppUser user = AppUserCreator.create(2L, "Jan", "Kowalski");
        when(appUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        AdminAppUserProfileEditDTO newUserProfileData = AdminAppUserProfileEditDTOCreator.create();
        // when
        AdminAppUserProfileEditDTO updatedUserProfile = adminAppUserServiceImpl.updateUserProfile(admin, user.getId(), newUserProfileData);
        // then
        assertThat(updatedUserProfile).isNotNull();
        assertThat(updatedUserProfile.getProfileImage()).isNull();
        assertThat(updatedUserProfile.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(updatedUserProfile.getLastName()).isEqualTo(user.getLastName());
        assertThat(updatedUserProfile.getDateOfBirth()).isEqualTo(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(updatedUserProfile.getCity()).isEqualTo(user.getCity());
        assertThat(updatedUserProfile.getBio()).isEqualTo(user.getBio());
    }

    @Test
    void shouldThrowExceptionIfAdminPasswordIsIncorrectWhenUpdatingUserPassword() {
        // given
        AppUser admin = AppUserCreator.create(1L, "Admin", "Admin", ROLE_ADMIN);
        AdminAppUserPasswordEditDTO newUserPasswordData = new AdminAppUserPasswordEditDTO(2L, "admin", "qwerty", "qwerty");
        // then
        assertThatThrownBy(() -> adminAppUserServiceImpl.updateUserPassword(admin, 2L, newUserPasswordData))
                .isInstanceOf(IncorrectCurrentPasswordException.class);
    }

    @Test
    void shouldUpdateUserPassword() {
        // given
        AppUser admin = AppUserCreator.create(1L, "Admin", "Admin", ROLE_ADMIN);
        AppUser user = AppUserCreator.create(2L, "Jan", "Kowalski");
        when(appUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        AdminAppUserPasswordEditDTO newUserPasswordData = new AdminAppUserPasswordEditDTO(user.getId(), "tests", "qwerty", "qwerty");
        when(passwordEncoder.matches(newUserPasswordData.getAdminPassword(), admin.getPassword())).thenReturn(true);
        // when
        adminAppUserServiceImpl.updateUserPassword(admin, 2L, newUserPasswordData);
        // then
        verify(passwordEncoder, times(1)).encode(newUserPasswordData.getNewPassword());
    }

    @Test
    void shouldThrowExceptionIfUserNotFoundWhenUpdatingUserPassword() {
        // given
        AppUser admin = AppUserCreator.create(1L, "Admin", "Admin", ROLE_ADMIN);
        AdminAppUserPasswordEditDTO newUserPasswordData = new AdminAppUserPasswordEditDTO(2L, "tests", "qwerty", "qwerty");
        when(passwordEncoder.matches(newUserPasswordData.getAdminPassword(), admin.getPassword())).thenReturn(true);
        // then
        assertThatThrownBy(() -> adminAppUserServiceImpl.updateUserPassword(admin, 2L, newUserPasswordData))
                .isInstanceOf(AppUserNotFoundException.class)
                .hasMessage(String.format("User with ID %s not found", newUserPasswordData.getId()));
    }

    @Test
    void shouldThrowExceptionIfAdminPasswordIsIncorrectWhenDeletingUser() {
        // given
        AppUser admin = AppUserCreator.create(1L, "Admin", "Admin", ROLE_ADMIN);
        AdminDeleteAppUserDTO deleteAppUserDTO = new AdminDeleteAppUserDTO(2L);
        deleteAppUserDTO.setAdminPassword("admin");
        when(passwordEncoder.matches(deleteAppUserDTO.getAdminPassword(), admin.getPassword())).thenReturn(false);
        // then
        assertThatThrownBy(() -> adminAppUserServiceImpl.deleteUser(admin, deleteAppUserDTO))
                .isInstanceOf(IncorrectCurrentPasswordException.class);
    }

    @Test
    void shouldDeleteUser() {
        // given
        AppUser admin = AppUserCreator.create(1L, "Admin", "Admin", ROLE_ADMIN);
        AdminDeleteAppUserDTO deleteAppUserDTO = new AdminDeleteAppUserDTO(2L);
        deleteAppUserDTO.setAdminPassword("tests");
        when(passwordEncoder.matches(deleteAppUserDTO.getAdminPassword(), admin.getPassword())).thenReturn(true);
        // when
        adminAppUserServiceImpl.deleteUser(admin, deleteAppUserDTO);
        // then
        verify(appUserRepository, times(1)).deleteById(eq(deleteAppUserDTO.getId()));
    }
}
