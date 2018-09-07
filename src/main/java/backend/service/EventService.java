package backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import backend.entity.Event;
import backend.repository.EventRepository;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private EventRepository eventRepository;
    private final String STATUS_PUBLISH = "publish";
    private final String STATUS_VERIFY = "verify";

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Iterable<Event> findAll() {
       return eventRepository.findAll();
    }

    public Long saveEvent(@NotNull Event event) {
        this.eventRepository.save(event);
        log.info("eventService::  event add to BD " + event.toString());
        return event.getEventId();
    }
    public void deleteEvent(@NotNull Event event) {
        log.info( "Event was remove " + event);
        this.eventRepository.delete(event);
    }
    public Iterable<Event> findVerifyEvent() {
       return this.eventRepository.findEventsByStatusEquals("verify");
    }

    public void publish(Event event){
        event.setStatus("publish");
        log.info("Event was published " + event);
    }

    @Scheduled(fixedDelay = 90000)
    public void removeExpEvents() {
        Iterable<Event> eventIterable2 = this.eventRepository.findEventsByEndEventLessThan(System.currentTimeMillis());
        eventIterable2.forEach(event -> {
            log.info("EventService: expired event " + event);
            deleteEvent(event);
        });
    }

    public Iterable<Event> findAllEventsInBorder(Double neLat, Double neLng, Double swLat, Double swLng) {
        return this.eventRepository.findEventsByLatitudeBetweenAndLongitudeBetweenAndStatusEquals(swLat,neLat,swLng,neLng, this.STATUS_VERIFY);
    }
}
