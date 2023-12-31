package pl.dreilt.iteventsapi.event.service;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dreilt.iteventsapi.appuser.model.AppUser;
import pl.dreilt.iteventsapi.event.dto.*;
import pl.dreilt.iteventsapi.event.exception.EventNotFoundException;
import pl.dreilt.iteventsapi.event.repository.EventRepository;
import pl.dreilt.iteventsapi.eventimage.model.EventImage;
import pl.dreilt.iteventsapi.event.mapper.EventCardDTOMapper;
import pl.dreilt.iteventsapi.event.mapper.EventDTOMapper;
import pl.dreilt.iteventsapi.event.mapper.EventEditDTOMapper;
import pl.dreilt.iteventsapi.event.mapper.ParticipantDTOMapper;
import pl.dreilt.iteventsapi.event.model.Event;
import pl.dreilt.iteventsapi.eventimage.service.EventImageService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizerEventServiceImpl implements OrganizerEventService {
    private final Logger logger = LoggerFactory.getLogger(OrganizerEventServiceImpl.class);
    private final EventRepository eventRepository;
    private final EventImageService eventImageService;

    public OrganizerEventServiceImpl(EventRepository eventRepository, EventImageService eventImageService) {
        this.eventRepository = eventRepository;
        this.eventImageService = eventImageService;
    }

    public EventDTO createEvent(AppUser currentUser, CreateEventDTO newEventData) {
        Event event = new Event();
        event.setName(newEventData.getName());
        event.setEventImage(eventImageService.createDefaultEventImage());
        event.setEventType(newEventData.getEventType());
        event.setDateTime(LocalDateTime.parse(newEventData.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        event.setLanguage(newEventData.getLanguage());
        event.setAdmission(newEventData.getAdmission());
        event.setCity(newEventData.getCity());
        event.setLocation(newEventData.getLocation());
        event.setAddress(newEventData.getAddress());
        event.setOrganizer(currentUser);
        event.setDescription(newEventData.getDescription());
        Event createdEvent = eventRepository.save(event);
        logger.info("Event [ID: " + createdEvent.getId() + "] created by user [ID: " + currentUser.getId() + "]");
        return EventDTOMapper.mapToEventDTO(createdEvent, currentUser);
    }

    public List<CityDTO> findAllCities() {
        List<String> cities = eventRepository.findAllCities();
        List<CityDTO> cityDTOs = new ArrayList<>();
        for (String city : cities) {
            CityDTO cityDTO = new CityDTO();
            cityDTO.setNameWithoutPlCharacters(getCityNameWithoutPlCharacters(city));
            cityDTO.setDisplayName(city);
            cityDTOs.add(cityDTO);
        }
        return cityDTOs;
    }

    public Page<EventCardDTO> findOrganizerEvents(AppUser currentUser, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findOrganizerEvents(currentUser, page));
    }

    public Page<EventCardDTO> findOrganizerEventsByCity(AppUser currentUser, String city, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findOrganizerEventsByCity(currentUser, city, page));
    }

    public EventEditDTO findEventToEdit(AppUser currentUser, Long id) {
        return EventEditDTOMapper.mapToEventEditDTO(returnEventIfCurrentUserIsOrganizer(currentUser, id));
    }

    @Transactional
    public EventEditDTO updateEvent(AppUser currentUser, Long id, EventEditDTO eventEditData) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            if (setEventFields(eventEditData, event)) {
                logger.info("Event [ID: " + event.getId() + "] updated by user [ID: " + currentUser.getId() + "]");
            }

            return EventEditDTOMapper.mapToEventEditDTO(event);
        }

        throw new EventNotFoundException("Event with ID " + id + " not found");
    }

    public Page<ParticipantDTO> findEventParticipants(AppUser currentUser, Long id, Pageable page) {
        Event event = returnEventIfCurrentUserIsOrganizer(currentUser, id);
        return ParticipantDTOMapper.mapToParticipantDTOs(event.getParticipants(), page);
    }

    @Transactional
    public Page<ParticipantDTO> removeParticipant(AppUser currentUser, Long eventId, Long participantId, Pageable page) {
        Event event = returnEventIfCurrentUserIsOrganizer(currentUser, eventId);
        Optional<AppUser> userOpt = event.getParticipants()
                .stream()
                .filter(participant -> participant.getId().equals(participantId))
                .findFirst();
        if (userOpt.isPresent()) {
            AppUser user = userOpt.get();
            event.removeParticipant(user);
            logger.info("User [ID: " + user.getId() + "] removed from event [ID: " + event.getId() + "] participants list by user [ID: " + currentUser.getId() + "]");
        }
        return ParticipantDTOMapper.mapToParticipantDTOs(event.getParticipants(), page);
    }

    private String getCityNameWithoutPlCharacters(String city) {
        city = city.toLowerCase();
        city = city.replace("\\s", "-");
        city = StringUtils.stripAccents(city);
        return city;
    }

    private Event returnEventIfCurrentUserIsOrganizer(AppUser currentUser, Long id) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            if (!currentUser.equals(event.getOrganizer())) {
                throw new AccessDeniedException("Access is denied");
            }

            return event;
        }

        throw new EventNotFoundException("Event with ID " + id + " not found");
    }

    private boolean setEventFields(EventEditDTO source, Event target) {
        boolean isUpdated = false;

        if (source.getName() != null && !source.getName().equals(target.getName())) {
            target.setName(source.getName());
            isUpdated = true;
        }
        if (source.getEventImage() != null && !source.getEventImage().isEmpty()) {
            Optional<EventImage> eventImage = eventImageService.updateEventImage(target, source.getEventImage());
            if (eventImage.isPresent()) {
                target.setEventImage(eventImage.get());
                isUpdated = true;
            }
        }
        if (source.getDateTime() != null && !source.getDateTime().equals(target.getDateTime().toString())) {
            target.setDateTime(LocalDateTime.parse(source.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            isUpdated = true;
        }
        if (source.getEventType() != null && source.getEventType() != target.getEventType()) {
            target.setEventType(source.getEventType());
            isUpdated = true;
        }
        if (source.getLanguage() != null && !source.getLanguage().equals(target.getLanguage())) {
            target.setLanguage(source.getLanguage());
            isUpdated = true;
        }
        if (source.getAdmission() != null && source.getAdmission() != target.getAdmission()) {
            target.setAdmission(source.getAdmission());
            isUpdated = true;
        }
        if (source.getCity() != null && !source.getCity().equals(target.getCity())) {
            target.setCity(source.getCity());
            isUpdated = true;
        }
        if (source.getLocation() != null && !source.getLocation().equals(target.getLocation())) {
            target.setLocation(source.getLocation());
            isUpdated = true;
        }
        if (source.getAddress() != null && !source.getAddress().equals(target.getAddress())) {
            target.setAddress(source.getAddress());
            isUpdated = true;
        }
        if (source.getDescription() != null && !source.getDescription().equals(target.getDescription())) {
            target.setDescription(source.getDescription());
            isUpdated = true;
        }

        return isUpdated;
    }
}
