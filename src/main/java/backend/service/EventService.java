package backend.service;

import backend.entity.Event;
import backend.forms.EventForm;
import backend.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;


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
        log.info("Event was remove " + event);
        this.eventRepository.delete(event);
    }

    public Event getEventById(@NotNull Long id) {
        return eventRepository.getEventByEventId(id);
    }

    public Iterable<Event> findVerifyEvent() {
        return this.eventRepository.findEventsByStatusEquals("verify");
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
            eventRepository.save(event);
            return event;
        }
    }

/*
    @Scheduled(fixedDelay = 20000)
    public void removeExpEvents() {
        Iterable<Event> eventIterable2 = this.eventRepository.findEventsByEndEventLessThan(System.currentTimeMillis());
        eventIterable2.forEach(event -> {
            log.info("EventService: expired event " + event);
            deleteEvent(event);
        });
    }*/
}
