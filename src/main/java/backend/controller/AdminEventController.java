package backend.controller;

import backend.common.Constants;
import backend.common.JWTUtils;
import backend.entity.Category;
import backend.entity.Event;
import backend.forms.EventForm;
import backend.service.CategoryService;
import backend.service.EmailService;
import backend.service.EventService;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
public class AdminEventController {

    private final EventService eventService;
    private final EmailService emailService;
    private final UserService userService;
    private final CategoryService categoryService;
    private Set<EventForm> listEventForm = new HashSet<>();
    private Set<String> listCategoryName;

    @Autowired
    public AdminEventController(EventService eventService, EmailService emailService, UserService userService, CategoryService categoryService) {
        this.eventService = eventService;
        this.emailService = emailService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin-get-events", method = RequestMethod.GET)
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
                            .categories(event.getCategories())
                            .location(event.getLocation())
                            .description(event.getEventDescription())
                            .build();
            listEventForm.add(eventForm);
        });
        return listEventForm;
    }

    @RequestMapping(value = "/admin-update-status-events", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStatus(@RequestBody EventForm eventForm) {
        Event event = eventService.updateStatus(eventForm);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            listEventForm.remove(eventForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/admin-add-events", method = RequestMethod.POST)
    public ResponseEntity<?> addEvent(@RequestBody EventForm eventForm, HttpServletRequest request) {
        final String token = request.getHeader(Constants.AUTH_HEADER).substring(7);
        List<String> dataToken = JWTUtils.getAudience(token);
        if (!dataToken.get(4).equals("ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (eventForm == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            if (convertDateToMills(eventForm.getStartStr()) == -1 || convertDateToMills(eventForm.getEndStr()) == -1) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Event event =   Event.builder()
                                .eventName(eventForm.getLabel())
                                .startEvent(convertDateToMills(eventForm.getStartStr()))
                                .endEvent(convertDateToMills(eventForm.getEndStr()))
                                .creationDate(System.currentTimeMillis())
                                .latitude(eventForm.getLat())
                                .longitude(eventForm.getLng())
                                .location(eventForm.getLocation())
                                .eventDescription(eventForm.getDescription())
                                .status("publish")
                                .userEntity(userService.getByEmail(dataToken.get(1)))
                            .build();
            eventService.saveEvent(event);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/admin-delete-event", method = RequestMethod.PUT)
    public ResponseEntity<?> deleteEvent(@RequestBody EventForm eventForm, HttpServletRequest request) {
        final String token = request.getHeader(Constants.AUTH_HEADER).substring(7);
        System.out.println(token);
        List<String> dataToken = JWTUtils.getAudience(token);
        if (!dataToken.get(4).equals("ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (eventForm == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Event event = eventService.getEventById(eventForm.getId());
            eventService.deleteEvent(event);
            listEventForm.remove(eventForm);
            emailService.sendMailInfo(event.getUserEntity().getEmail(), event.getLocation());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    private long convertDateToMills(String date) {
        try {
            String myDate = date + " 01:00:00";
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
