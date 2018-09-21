package backend;

import backend.entity.Coordinate;
import backend.forms.UserFormData;
import backend.service.RouteService;
import backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


    }

    @Bean
    public CommandLineRunner demo(UserService userService) {
        return (args) -> {
            userService.saveUser(new UserFormData("alex", "1234", "alex", "test"));
            /*log.info("Application is running");*/
           /* service.clearBd();
            Coordinate coord1 = new Coordinate(55.10, 37.00,1L);
            Coordinate coord2 = new Coordinate(55.50, 37.50, 1000L);
            Coordinate coord3 = new Coordinate(55.90, 37.90, 2000000L);


            List<Coordinate> listCoordinates = Arrays.asList(coord1, coord2, coord3);
            service.saveRoute(listCoordinates, 1L, 1L);
            coord1 = new Coordinate(56.10, 38.00, 3000L);
            coord2 = new Coordinate(56.50, 38.50, 3500L);
            coord3 = new Coordinate(56.90, 38.90, 4000000L);
            listCoordinates = Arrays.asList(coord1, coord2, coord3);
            service.saveRoute(listCoordinates, 1L, 2L);*/

    //

          /*
            service.saveRoute(listCoordinates, 1L, 234324235236123L);
            service.saveRoute(listCoordinates, 1L, 46457567456345150L);*/
/*
            Iterable<Route> list = service.findRutesForCurentDates(1536791916000L, 1536794606000L);
            userService.saveUser("alex", "1234");
            User user = userService.findByLogin("alex");
            log.info("-------------------------------" + user.toString());*/
        };
    }
}
