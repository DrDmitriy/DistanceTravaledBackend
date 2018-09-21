package backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Class represent a node entity in the noSQL database Neo4j
 * Graph storage of routes and coordinates with Long primary key
 * Route-node is a main node. Dependent node entity is Coordinate.
 * Route has one-directed relations determine the order for cascade
 * insert and delete
 */

@NodeEntity
public class Route {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long routeID;

    @Relationship(type = "ROUTING", direction = Relationship.OUTGOING)
    private List<Coordinate> route;

    @JsonIgnore
    private Long userID;
    @JsonIgnore
    private Long dateOfCreation;

    public Route() {
    }

    public Route(Long userID) {
        this.userID = userID;
    }

    public Route(List<Coordinate> route, Long userID) {
        this.route = route;
        this.userID = userID;
    }

    public Route(Route route){
        this.route = route.route;
        this.dateOfCreation = route.dateOfCreation;
        this.userID = route.userID;
    }

    public Route(List<Coordinate> route, Long userID, Long dateOfCreation){
        this.route = route;
        this.userID = userID;
        this.dateOfCreation = dateOfCreation;
    }

    public String toString() {
        return String.format(" Route: id = %d, user = '%d', number of coords = '%d'",
                routeID, userID, route.size());
    }

    /**
     * methods add new Coordinate to the list initializing if it necessary
     * @param next
     */

    public void addCoordinate(Coordinate next) {
        if (route == null) {
            route = new ArrayList<>();
        }
        route.add(next);
    }

    public void addAllroute(Iterable<Coordinate> nextroute) {
        if (route == null) {
            route = new ArrayList<>();
        }
        nextroute.forEach(route::add);
    }



    public Long getRouteID() {
        return routeID;
    }

    public void setRouteID(Long routeID) {
        this.routeID = routeID;
    }

    public List<Coordinate> getroute() {
        return route;
    }

    public void setroute(List<Coordinate> route) {
        this.route = route;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setDateOfCreation(Long dateOfCreation){ this.dateOfCreation = dateOfCreation; }

    public Long getDateOfCreation(){ return this.dateOfCreation; }
}
