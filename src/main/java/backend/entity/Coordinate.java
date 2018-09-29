package backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Class represent a related node entity in the noSQL database Neo4j
 * Graph storage of routes and coordinates with Long primary key
 * Route-node is a main node. Dependent node entity is Coordinate.
 * Coordinate node doesn't have link to the route or user.
 * Coordinates are being created by cascade
 */

@NodeEntity
public class Coordinate {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long coordID;


    private Double lat;
    private Double lng;
    private Long date;

    public Coordinate() {
    }

    public Coordinate(Double lat, Double lng, Long date) {
        this.lat = lat;
        this.lng = lng;
        this.date = date;
    }

    public Long getCoordID() {
        return coordID;
    }

    public void setCoordID(Long coordID) {
        this.coordID = coordID;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
