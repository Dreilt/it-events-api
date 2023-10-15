package pl.dreilt.iteventsapi.eventimage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dreilt.iteventsapi.eventimage.model.EventImage;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {
}
