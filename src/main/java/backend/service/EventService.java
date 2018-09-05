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

    @Scheduled(fixedDelay = 20000)
    public void removeExpEvents() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Iterable<Event> eventIterable2 = this.eventRepository.findEventsByEndEventBefore(currentTime);
        eventIterable2.forEach(event -> {
            log.info("EventService: expired event " + event);
            deleteEvent(event);
        });
    }
}
