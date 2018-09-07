package backend.controller;

import backend.controller.requestbody.EventBody;
import backend.entity.Category;
import backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import backend.entity.Event;
import backend.service.EventService;
import backend.service.UserService;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class EventController {
    private EventService eventService;
    private UserService userService;
    private CategoryService categoryService;

    @Autowired
    public EventController(EventService eventService, UserService userService, CategoryService categoryService) {
        this.eventService = eventService;
        this.userService = userService;
        this.categoryService = categoryService;

    }

    @PostMapping(value = "/events")
    @CrossOrigin("*")
    public ResponseEntity addEvent(@RequestBody EventBody eventBody) { //create EventBody to Event Throw constructor
        eventBody.setUser(userService.findById(eventBody.getuserId()).get());

       /* Arrays.stream(eventBody.getCategory().split(",")).forEach(id ->
                eventBody.addCategory(categoryService.findById(Long.valueOf(id))));*/

        Event event = new Event(eventBody);
        eventService.saveEvent(event);
        eventService.findVerifyEvent().forEach(event1 -> System.out.println("Event Verify " + event1));
        return new ResponseEntity(HttpStatus.CREATED);
    }


    /*  @PostMapping(value = "/events")
    @CrossOrigin("*")
    public ResponseEntity addEvent(@RequestBody String eventBody) { //create EventBody to Event Throw constructor
        System.out.println("!!!!   " + eventBody);
         return new ResponseEntity(HttpStatus.CREATED);
    }*/

  /*  @GetMapping(value = "/events")
    @CrossOrigin("*")
    public @ResponseBody List<Event> getEvents() {
        List<Event> list = new ArrayList();
        Iterable<Event> events = eventService.findAll();
        events.forEach(event -> list.add(event));
        return list;
    }*/

    @GetMapping(value = "/events")
    @CrossOrigin("*")
    public @ResponseBody
    List<Event> getAll(WebRequest webRequest) {
        List<Double> requestParams = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();

        Map<String, String[]> params = webRequest.getParameterMap();
        params.forEach((s, strings) -> {
            requestParams.add(Double.parseDouble(strings[0]));
            System.out.println(strings[0]);
        });
        this.eventService.findAllEventsInBorder(requestParams.get(0), requestParams.get(1), requestParams.get(2), requestParams.get(3))
                .forEach(event -> eventList.add(event));
        return eventList;
    }
}
