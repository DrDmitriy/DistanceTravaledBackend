package backend.repository;

import org.springframework.data.repository.CrudRepository;
import backend.entity.Event;

import java.sql.Timestamp;
import java.util.Set;

public interface EventRepository extends CrudRepository<Event, Long> {
    Iterable<Event> findEventsByEndEventLessThan(Long time);
    Iterable<Event> findEventsByStatusEquals(String status);
    Iterable<Event> findEventsByLatitudeBetweenAndLongitudeBetweenAndStatusEquals(Double swLat, Double neLat, Double swLng, Double neLng, String status);
}
