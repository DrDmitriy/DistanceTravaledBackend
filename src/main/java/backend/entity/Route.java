package backend.entity;

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
    private Long routeID;

    @Relationship(type = "ROUTING", direction = Relationship.OUTGOING)
    private List<Coordinate> coordinates;

    private Long userID;

    public Route() {
    }

    public Route(Long userID) {
        this.userID = userID;
    }

    public Route(List<Coordinate> coordinates, Long userID) {
        this.coordinates = coordinates;
        this.userID = userID;
    }

    public String toString() {
        return String.format(" Route: id = %d, user = '%d', number of coords = '%d'",
                routeID, userID, coordinates.size());
    }

    /**
     * methods add new Coordinate to the list initializing if it necessary
     * @param next
     */

    public void addCoordinate(Coordinate next) {
        if (coordinates == null) {
            coordinates = new ArrayList<>();
        }
        coordinates.add(next);
    }

    public void addAllCoordinates(Iterable<Coordinate> nextCoordinates) {
        if (coordinates == null) {
            coordinates = new ArrayList<>();
        }
        nextCoordinates.forEach(coordinates::add);
    }



    public Long getRouteID() {
        return routeID;
    }

    public void setRouteID(Long routeID) {
        this.routeID = routeID;
    }


    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
