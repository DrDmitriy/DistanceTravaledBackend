package backend.repository;

import backend.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
    Event getEventByEventId(Long id);
    Iterable<Event> findEventsByEndEventLessThan(Long time);
    Iterable<Event> findEventsByStatusEquals(String status);
    Iterable<Event> findEventsByLatitudeBetweenAndLongitudeBetweenAndStatusEquals(Double swLat, Double neLat, Double swLng, Double neLng, String status);
}
