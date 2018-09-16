package backend.controller;

import backend.common.Constants;
import backend.common.JWTUtils;
import backend.controller.requestbody.EventBody;
import backend.entity.Event;
import backend.service.CategoryService;
import backend.service.EventService;
import backend.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
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


    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public ResponseEntity addEvent(@RequestBody EventBody eventBody, HttpServletRequest request) { //create EventBody to Event Throw constructor
        final String token = request.getHeader(Constants.AUTH_HEADER).substring(7);
        List<String> dataToken = JWTUtils.getAudience(token);
        //eventBody.setUserEntity(userService.findById(eventBody.getUserId()).get());

        eventBody.setUserEntity(userService.findById(Long.valueOf(dataToken.get(0))).get());

/*

    @PostMapping(value = "/events")
    //@CrossOrigin("*")
    public ResponseEntity addEvent(@RequestBody EventBody eventBody) { //create EventBody to Event Throw constructor


        eventBody.setUserEntity(userService.findById(eventBody.getUserId()).get());
*/

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

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<Event> getEvents(HttpServletResponse response) {
/*
    @GetMapping(value = "/events")
    //@CrossOrigin("*")
    public List<Event> getEvents(HttpServletResponse response) {
*/
        List<Event> list = new ArrayList<>();
        Iterable<Event> events = eventService.findAll();
        events.forEach(list::add);
        response.setStatus(HttpStatus.OK.value());
        return list;
    }
}
