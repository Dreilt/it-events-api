package pl.dreilt.iteventsapi.creator;

import pl.dreilt.iteventsapi.appuser.model.AppUser;
import pl.dreilt.iteventsapi.event.enumeration.AdmissionType;
import pl.dreilt.iteventsapi.event.enumeration.EventType;
import pl.dreilt.iteventsapi.event.model.Event;
import pl.dreilt.iteventsapi.eventimage.model.EventImage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventCreator {

    public static Event create(String name, LocalDateTime dateTime, AppUser organizer) {
        return Event.builder()
                .name(name)
                .eventImage(EventImageCreator.createDefaultEventImage())
                .eventType(EventType.MEETING)
                .dateTime(dateTime)
                .language("polski")
                .admission(AdmissionType.FREE)
                .city("Rzeszów")
                .location("WSIiZ")
                .address("Sucharskiego 2, 35-225 Rzeszów")
                .organizer(organizer)
                .description("Spotkanie rzeszowskiej grupy pasjonatów języka Java.")
                .participants(new ArrayList<>())
                .build();
    }

    public static Event create(String name, EventImage eventImage, LocalDateTime dateTime, AppUser organizer) {
        return Event.builder()
                .name(name)
                .eventImage(eventImage)
                .eventType(EventType.MEETING)
                .dateTime(dateTime)
                .language("polski")
                .admission(AdmissionType.FREE)
                .city("Rzeszów")
                .location("WSIiZ")
                .address("Sucharskiego 2, 35-225 Rzeszów")
                .organizer(organizer)
                .description("Spotkanie rzeszowskiej grupy pasjonatów języka Java.")
                .participants(new ArrayList<>())
                .build();
    }

    public static Event create(String name, EventImage eventImage, LocalDateTime dateTime, AppUser organizer, List<AppUser> participants) {
        return Event.builder()
                .name(name)
                .eventImage(eventImage)
                .eventType(EventType.MEETING)
                .dateTime(dateTime)
                .language("polski")
                .admission(AdmissionType.FREE)
                .city("Rzeszów")
                .location("WSIiZ")
                .address("Sucharskiego 2, 35-225 Rzeszów")
                .organizer(organizer)
                .description("Spotkanie rzeszowskiej grupy pasjonatów języka Java.")
                .participants(participants)
                .build();
    }

    public static Event create(Long id, String name, LocalDateTime dateTime, AppUser organizer) {
        return Event.builder()
                .id(id)
                .name(name)
                .eventImage(EventImageCreator.createDefaultEventImage(id))
                .eventType(EventType.MEETING)
                .dateTime(dateTime)
                .language("polski")
                .admission(AdmissionType.FREE)
                .city("Rzeszów")
                .location("WSIiZ")
                .address("Sucharskiego 2, 35-225 Rzeszów")
                .organizer(organizer)
                .description("Spotkanie rzeszowskiej grupy pasjonatów języka Java.")
                .participants(new ArrayList<>())
                .build();
    }

    public static Event create(Long id, String name, LocalDateTime dateTime, AppUser organizer, List<AppUser> participants) {
        return Event.builder()
                .id(id)
                .name(name)
                .eventImage(EventImageCreator.createDefaultEventImage(id))
                .eventType(EventType.MEETING)
                .dateTime(dateTime)
                .language("polski")
                .admission(AdmissionType.FREE)
                .city("Rzeszów")
                .location("WSIiZ")
                .address("Sucharskiego 2, 35-225 Rzeszów")
                .organizer(organizer)
                .description("Spotkanie rzeszowskiej grupy pasjonatów języka Java.")
                .participants(participants)
                .build();
    }
}
