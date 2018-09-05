package backend.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.sql.Timestamp;

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
    private Long coordID;
    private Double latitude;
    private Double longitude;
    private Timestamp timestamp;

    public Coordinate(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        //this.route = route;
    }

    private Coordinate() {
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


}
