package backend.controller;

import backend.entity.Event;
import backend.form.EventForm;
import backend.service.EventService;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

@RestController
public class AdminEventController {

    private final EventService eventService;
    private final UserService userService;
    private Set<EventForm> listEventForm = new HashSet<>();

    @Autowired
    public AdminEventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping(value = "/admin-get-events")
    @CrossOrigin("*")
    public Set<EventForm> getEvents() {

        Iterable<Event> iterable = eventService.findVerifyEvent();
        iterable.forEach(event -> {
            EventForm eventForm =
                    EventForm.builder()
                            .id(event.getEventId())
                            .label(event.getEventName())
                            .creation(event.getCreationDate())
                            .start(event.getStartEvent())
                            .end(event.getEndEvent())
                            .lat(event.getLatitude())
                            .lng(event.getLongitude())
                            .location(event.getLocation())
                            .description(event.getEventDiscription())
                            .build();
            listEventForm.add(eventForm);
        });
        return listEventForm;
    }

    @PutMapping(value = "/admin-update-status-events")
    @CrossOrigin("*")
    public ResponseEntity<?> updateStatus(@RequestBody EventForm eventForm) {
        Event event = eventService.updateStatus(eventForm);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            listEventForm.remove(eventForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping(value = "/admin-add-events")
    @CrossOrigin("*")
    public ResponseEntity<?> addEvent(@RequestBody EventForm eventForm) {
        if (eventForm == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            if (convertDateToMills(eventForm.getStartStr()) == -1 || convertDateToMills(eventForm.getEndStr()) == -1) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            System.out.println(convertDateToMills(eventForm.getStartStr()));
            Event event =
                            Event.builder()
                                .eventName(eventForm.getLabel())
                                .startEvent(convertDateToMills(eventForm.getStartStr()))
                                .endEvent(convertDateToMills(eventForm.getEndStr()))
                                .creationDate(System.currentTimeMillis())
                                .latitude(eventForm.getLat())
                                .longitude(eventForm.getLng())
                                .location(eventForm.getLocation())
                                .eventDiscription(eventForm.getDescription())
                                .status("public")
                                .user(userService.getByLogin("Asdsd@sdas.cso"))
                            .build();

            eventService.addEvent(event);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    private long convertDateToMills(String date) {
        try {
            String myDate = date + " 00:00:00";
            LocalDateTime localDateTime = LocalDateTime.parse(myDate,
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

            return localDateTime
                    .atZone(ZoneId.systemDefault())
                    .toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            return -1;
        }
    }
}
