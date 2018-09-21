package backend.controller;

import backend.common.Constants;
import backend.common.JWTUtils;
import backend.controller.requestbody.EventBody;
import backend.entity.Event;
import backend.entity.UserEntity;
import backend.service.CategoryService;
import backend.service.EventService;
import backend.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        eventBody.setUserEntity(userService.findById(Long.valueOf(dataToken.get(0))).get());

        Event event = new Event(eventBody);
        eventService.saveEvent(event);
        eventService.findVerifyEvent().forEach(event1 -> System.out.println("Event Verify " + event1));

        return new ResponseEntity(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<Event> getEvents(WebRequest webRequest, HttpServletResponse response) {
        List<Double> requestParams = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();

        Map<String, String[]> params = webRequest.getParameterMap();
        params.forEach((s, strings) -> {
            requestParams.add(Double.parseDouble(strings[0]));
            System.out.println(strings[0]);
        });
        this.eventService.findAllEventsInBorder(requestParams.get(0), requestParams.get(1), requestParams.get(2), requestParams.get(3))
                .forEach(event -> eventList.add(event));
        response.setStatus(HttpStatus.OK.value());
        return eventList;
    }

   /* @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<Event> getEvents(HttpServletResponse response) {
        List<Event> list = new ArrayList<>();
        //Iterable<Event> events = eventService.findAll();
        Iterable<Event> events = eventService.findAllPublish();
        events.forEach(list::add);
        response.setStatus(HttpStatus.OK.value());
        return list;
    }*/

    @RequestMapping(value = "/myEvents", method = RequestMethod.GET)
    public List<Event> getAllMyEvents(HttpServletResponse response, HttpServletRequest request) {
        List<Event> list = new ArrayList<>();
        final String token = request.getHeader(Constants.AUTH_HEADER).substring(7);
        Long userId = Long.valueOf(JWTUtils.getAudience(token).get(0));
        UserEntity userEntity = (userService.findById(userId)).get();
        Iterable<Event> events = eventService.findAllUserVerifyEvents(userEntity);
        events.forEach(list::add);
        response.setStatus(HttpStatus.OK.value());
        return list;
    }
}
