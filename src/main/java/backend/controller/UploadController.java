package backend.controller;

import backend.common.JWTUtils;
import backend.controller.requestbody.RouteForTransfer;
import backend.entity.Route;
import backend.service.RouteService;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UploadController {
    UserService userService;
    RouteService routeService;

    @Autowired
    public UploadController(UserService userService, RouteService routeService){
        this.routeService = routeService;
        this.userService = userService;
    }

    @Qualifier("userService")
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> readCoord(@RequestBody RouteForTransfer routeForTransfer){
        Long userId = Long.valueOf(JWTUtils.getAudience(routeForTransfer.getToken().split(" ")[1]).get(0));
        //Long userId = new Long(JWTUtils.getAudience(routeForTransfer.getToken()).get(0));
        for(Route route : routeForTransfer.getUserCoords()){
            Route toSafe = new Route(route);
            toSafe.setDateOfCreation(route.getroute().get(0).getDate());
            toSafe.setUserID(userId);
            routeService.saveRoute(toSafe);
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
