package backend.service;

import backend.entity.Event;
import backend.entity.UserEntity;
import backend.forms.EventForm;
import backend.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;


@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private EventRepository eventRepository;
    private final CategoryService categoryService;
    private final String STATUS_PUBLISH = "publish";
    private final String STATUS_VERIFY = "verify";

    @Autowired
    public EventService(EventRepository eventRepository, CategoryService categoryService) {
        this.eventRepository = eventRepository;
        this.categoryService = categoryService;
    }

    public Iterable<Event> findAll() {
        return eventRepository.findAll();
    }

    public void saveEvent(@NotNull Event event) {
        this.eventRepository.save(event);
        log.info("eventService::  event add to BD " + event.toString());
    }

    public void deleteEvent(@NotNull Event event) {
        log.info("Event was remove " + event);
        this.eventRepository.delete(event);
    }

    public Event getEventById(@NotNull Long id) {
        return eventRepository.getEventByEventId(id);
    }

    public Iterable<Event> findVerifyEvent() {
        return this.eventRepository.findEventsByStatusEquals("verify");
    }

    public Iterable<Event> findAllUserVerifyEvents(UserEntity userEntity) {
        return this.eventRepository.findEventsByUserEntityEqualsAndStatusEquals(userEntity, STATUS_VERIFY);
    }

    public void publish(Event event) {
        event.setStatus("publish");
        log.info("Event was published " + event);
    }

    public Event updateStatus(EventForm eventForm) {
        if (eventForm == null) {
            return null;
        } else {
            Event event = eventRepository.getEventByEventId(eventForm.getId());
            event.setStatus("publish");
            event.setLocation(eventForm.getLocation());
            event.setEventDescription(eventForm.getDescription());
            event.setEventName(eventForm.getLabel());
            event.setCategories(eventForm.getCategories().stream().map(categoryService::findByName).collect(Collectors.toSet()));
            eventRepository.save(event);
            return event;
        }
    }

    @Scheduled(fixedDelay = 900000)
    public void removeExpEvents() {
        Iterable<Event> eventIterable2 = this.eventRepository.findEventsByEndEventLessThan(System.currentTimeMillis());
        eventIterable2.forEach(event -> {
            log.info("EventService: expired event " + event);
            deleteEvent(event);
        });
    }

    public Iterable<Event> findAllEventsInBorder(Double neLat, Double neLng, Double swLat, Double swLng) {
        return this.eventRepository.findEventsByLatitudeBetweenAndLongitudeBetweenAndStatusEquals(swLat, neLat, swLng, neLng, this.STATUS_PUBLISH);
    }
}
