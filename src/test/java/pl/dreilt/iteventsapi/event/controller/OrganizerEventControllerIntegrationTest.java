package pl.dreilt.iteventsapi.event.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pl.dreilt.iteventsapi.appuser.model.AppUser;
import pl.dreilt.iteventsapi.appuser.repository.AppUserRepository;
import pl.dreilt.iteventsapi.creator.*;
import pl.dreilt.iteventsapi.event.dto.CreateEventDTO;
import pl.dreilt.iteventsapi.event.dto.EventDTO;
import pl.dreilt.iteventsapi.event.dto.EventEditDTO;
import pl.dreilt.iteventsapi.event.model.Event;
import pl.dreilt.iteventsapi.event.repository.EventRepository;
import pl.dreilt.iteventsapi.eventimage.repository.EventImageRepository;
import pl.dreilt.iteventsapi.profileimage.repository.ProfileImageRepository;

import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.dreilt.iteventsapi.appuser.model.Role.ROLE_ORGANIZER;

@SpringBootTest
@AutoConfigureMockMvc
class OrganizerEventControllerIntegrationTest {
    static final LocalDateTime DATE_TIME = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).withHour(18).withMinute(0);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private ProfileImageRepository profileImageRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventImageRepository eventImageRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @WithMockUser(username = "jannowak@example.com", roles = {"ORGANIZER"})
    void shouldReturnCreatedEvent() throws Exception {
        // given
        AppUser organizer = AppUserCreator.create("Jan", "Nowak", profileImageRepository.save(ProfileImageCreator.createDefaultProfileImage()), ROLE_ORGANIZER);
        appUserRepository.save(organizer);
        CreateEventDTO newEventData = CreateEventDTOCreator.create(DATE_TIME);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/organizer/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEventData));;
        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        // then
        assertThat(result.getResponse().getHeader("Location")).isNotNull();
        EventDTO createdEvent = objectMapper.readValue(result.getResponse().getContentAsString(), EventDTO.class);
        assertThat(createdEvent.getId()).isNotNull();
        assertThat(createdEvent.getName()).isEqualTo(newEventData.getName());
    }

    @Test
    @Transactional
    @WithMockUser(username = "jannowak@example.com", roles = {"ORGANIZER"})
    void shouldReturnOrganizerEvents() throws Exception {
        // given
        AppUser organizer = AppUserCreator.create("Jan", "Nowak", profileImageRepository.save(ProfileImageCreator.createDefaultProfileImage()), ROLE_ORGANIZER);
        appUserRepository.save(organizer);
        List<Event> events = List.of(
                EventCreator.create("Java Dev Talks #1", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME, organizer),
                EventCreator.create("Java Dev Talks #2", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(1L), organizer),
                EventCreator.create("Java Dev Talks #3", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(2L), organizer),
                EventCreator.create("Java Dev Talks #4", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(3L), organizer),
                EventCreator.create("Java Dev Talks #5", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(4L), organizer),
                EventCreator.create("Java Dev Talks #6", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(5L), organizer),
                EventCreator.create("Java Dev Talks #7", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(6L), organizer),
                EventCreator.create("Java Dev Talks #8", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(7L), organizer),
                EventCreator.create("Java Dev Talks #9", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(8L), organizer),
                EventCreator.create("Java Dev Talks #10", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(9L), organizer)
        );
        eventRepository.saveAll(events);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/organizer/events");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(10)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "jannowak@example.com", roles = {"ORGANIZER"})
    void shouldReturnOrganizerEventsByCity() throws Exception {
        // given
        AppUser organizer = AppUserCreator.create("Jan", "Nowak", profileImageRepository.save(ProfileImageCreator.createDefaultProfileImage()), ROLE_ORGANIZER);
        appUserRepository.save(organizer);
        List<Event> events = List.of(
                EventCreator.create("Java Dev Talks #1", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME, organizer),
                EventCreator.create("Java Dev Talks #2", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(1L), organizer),
                EventCreator.create("Java Dev Talks #3", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(2L), organizer),
                EventCreator.create("Java Dev Talks #4", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(3L), organizer),
                EventCreator.create("Java Dev Talks #5", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(4L), organizer),
                EventCreator.create("Java Dev Talks #6", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(5L), organizer),
                EventCreator.create("Java Dev Talks #7", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(6L), organizer),
                EventCreator.create("Java Dev Talks #8", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(7L), organizer),
                EventCreator.create("Java Dev Talks #9", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(8L), organizer),
                EventCreator.create("Java Dev Talks #10", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME.plusWeeks(9L), organizer)
        );
        eventRepository.saveAll(events);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/organizer/events/cities/rzeszow");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(10)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "jannowak@example.com", roles = {"ORGANIZER"})
    void shouldReturnEventToEdit() throws Exception {
        // given
        AppUser organizer = AppUserCreator.create("Jan", "Nowak", profileImageRepository.save(ProfileImageCreator.createDefaultProfileImage()), ROLE_ORGANIZER);
        appUserRepository.save(organizer);
        Event event = EventCreator.create("Java Dev Talks #1", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME, organizer);
        eventRepository.save(event);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/organizer/events/" + event.getId() + "/edit");
        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        EventEditDTO returnedEventToEdit = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), EventEditDTO.class);
        assertThat(returnedEventToEdit).isNotNull();
        assertThat(returnedEventToEdit.getName()).isEqualTo(event.getName());
        assertThat(returnedEventToEdit).isNotNull();
        assertThat(returnedEventToEdit.getImageType()).isEqualTo(event.getEventImage().getFileType());
        assertThat(returnedEventToEdit.getImageData()).isEqualTo(Base64.getEncoder().encodeToString(event.getEventImage().getFileData()));
        assertThat(returnedEventToEdit.getEventType()).isEqualTo(event.getEventType());
        assertThat(returnedEventToEdit.getDateTime()).isEqualTo(event.getDateTime().toString());
        assertThat(returnedEventToEdit.getLanguage()).isEqualTo(event.getLanguage());
        assertThat(returnedEventToEdit.getAdmission()).isEqualTo(event.getAdmission());
        assertThat(returnedEventToEdit.getCity()).isEqualTo(event.getCity());
        assertThat(returnedEventToEdit.getLocation()).isEqualTo(event.getLocation());
        assertThat(returnedEventToEdit.getAddress()).isEqualTo(event.getAddress());
        assertThat(returnedEventToEdit.getDescription()).isEqualTo(event.getDescription());
    }

    @Test
    @Transactional
    @WithMockUser(username = "jannowak@example.com", roles = {"ORGANIZER"})
    void shouldUpdateEvent() throws Exception {
        // given
        AppUser organizer = AppUserCreator.create("Jan", "Nowak", profileImageRepository.save(ProfileImageCreator.createDefaultProfileImage()), ROLE_ORGANIZER);
        appUserRepository.save(organizer);
        Event event = EventCreator.create("Java Dev Talks #1", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME, organizer);
        eventRepository.save(event);
        EventEditDTO newEventData = EventEditDTOCreator.create(DATE_TIME);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/api/v1/organizer/events/" + event.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEventData));
        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        EventEditDTO updatedEvent = objectMapper.readValue(result.getResponse().getContentAsString(), EventEditDTO.class);
        assertThat(updatedEvent.getName()).isEqualTo(newEventData.getName());
        assertThat(updatedEvent.getEventImage()).isNull();
        assertThat(updatedEvent.getEventType()).isEqualTo(newEventData.getEventType());
        assertThat(updatedEvent.getDateTime()).isEqualTo(newEventData.getDateTime());
        assertThat(updatedEvent.getLanguage()).isEqualTo(newEventData.getLanguage());
        assertThat(updatedEvent.getAdmission()).isEqualTo(newEventData.getAdmission());
        assertThat(updatedEvent.getCity()).isEqualTo(newEventData.getCity());
        assertThat(updatedEvent.getLocation()).isEqualTo(newEventData.getLocation());
        assertThat(updatedEvent.getAddress()).isEqualTo(newEventData.getAddress());
        assertThat(updatedEvent.getDescription()).isEqualTo(newEventData.getDescription());
    }

    @Test
    @Transactional
    @WithMockUser(username = "jannowak@example.com", roles = {"ORGANIZER"})
    void shouldReturnEventParticipants() throws Exception {
        // given
        AppUser organizer = AppUserCreator.create("Jan", "Nowak", profileImageRepository.save(ProfileImageCreator.createDefaultProfileImage()), ROLE_ORGANIZER);
        appUserRepository.save(organizer);
        Event event = EventCreator.create("Java Dev Talks #1", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME, organizer);
        AppUser user = AppUserCreator.create("Jan", "Kowalski", profileImageRepository.save(ProfileImageCreator.createDefaultProfileImage()));
        event.addParticipant(user);
        eventRepository.save(event);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/organizer/events/" + event.getId() + "/participants");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "jannowak@example.com", roles = {"ORGANIZER"})
    void shouldRemoveUserFromEventParticipantsList() throws Exception {
        // given
        AppUser organizer = AppUserCreator.create("Jan", "Nowak", profileImageRepository.save(ProfileImageCreator.createDefaultProfileImage()), ROLE_ORGANIZER);
        appUserRepository.save(organizer);
        Event event = EventCreator.create("Java Dev Talks #1", eventImageRepository.save(EventImageCreator.createDefaultEventImage()), DATE_TIME, organizer);
        AppUser user = AppUserCreator.create("Jan", "Kowalski", profileImageRepository.save(ProfileImageCreator.createDefaultProfileImage()));
        appUserRepository.save(user);
        event.addParticipant(user);
        eventRepository.save(event);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/api/v1/organizer/events/" + event.getId() + "/participants/" + user.getId());
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }
}
