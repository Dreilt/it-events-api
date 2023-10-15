package pl.dreilt.iteventsapi.eventimage.service;

import org.springframework.web.multipart.MultipartFile;
import pl.dreilt.iteventsapi.event.model.Event;
import pl.dreilt.iteventsapi.eventimage.model.EventImage;

import java.util.Optional;

public interface EventImageService {

    EventImage createDefaultEventImage();

    Optional<EventImage> updateEventImage(Event event, MultipartFile newEventImage);
}
