package backend.service;

import backend.entity.Coordinate;
import backend.entity.Route;
import backend.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class represent service layer for interaction with Neo4j noSQL database intended
 * to be a routes storage.
 * Every Route  is a <code>NodeEntity</code> and contains a list of subordinate node entities
 * - Coordinate.
 * * provides all necessary methods wrapping interaction with repositories
 * Besides CRUD methods, has custom method <code>updateCoordinatesForRoute</code>
 */
@Service
public class RouteService {

    private static final Logger log = LoggerFactory.getLogger(RouteService.class);

    private RouteRepository repository;

    @Autowired
    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

    public Long saveRoute(List<Coordinate> coordinates, Long userID, Long dateOfCreation) {
        Route route = new Route(coordinates, userID, dateOfCreation);
        log.info("routeService:: add new route " + route.toString());
        repository.save(route);
        return route.getRouteID();
    }


    public Long saveRoute(Route route) {
        repository.save(route);
        return route.getRouteID();
    }

    public Optional<Route> findById(Long id) {
        return repository.findById(id);
    }

    public List<Route> findAll() {
        List<Route> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    public Iterable<Route> findAllUserRoutes(Long userId) {
        return this.repository.findAllByUserIDEquals(userId);
    }

    public Iterable<Route> findRutesForCurentDates(Long dateBegin, Long dateEnd) {
        Iterable<Route> list;
        list = repository.findAllBydateOfCreationBetween(dateBegin, dateEnd);
        return list;
    }

}
