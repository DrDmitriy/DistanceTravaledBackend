package backend.controller;


import backend.common.Constants;
import backend.common.JWTUtils;
import backend.entity.Route;
import backend.service.RouteService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;


import java.util.ArrayList;
import java.util.List;

@Controller
public class RouteController {

    private RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService){

        this.routeService = routeService;
    }

    @GetMapping(value = "/routes")
    public @ResponseBody List<Route> getRoutes(WebRequest requestParam, HttpServletRequest request){
        final String token = request.getHeader(Constants.AUTH_HEADER).substring(7);
        List<String> dataToken = JWTUtils.getAudience(token);

        List<Route> routesList = new ArrayList<>();
        List<Long> dateList = new ArrayList<>();

        requestParam.getParameterMap().forEach((name, date) -> {
            dateList.add(Long.valueOf(date[0]));
        });
        if(dateList.size() == 2) {
            routeService.findRutesForCurentDates(dateList.get(0), dateList.get(1)).forEach(routesList::add);
            return routesList;
        }
        else {
            routeService.findAllUserRoutes((Long.valueOf(dataToken.get(0)))).forEach((routesList::add));
            return routesList;
        }
    }
}
