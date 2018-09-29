package backend.entity;

import backend.controller.requestbody.EventBody;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"categories", "userEntity"})
@Data
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;
    private String eventName;
    @Type(type = "text")
    private String eventDescription;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    @JsonIgnore
    private UserEntity userEntity;
    private Double latitude;
    private Double longitude;
    private String location;
    private Long startEvent;
    private Long endEvent;
    private Long creationDate;
    private Double userRating; // Double
    private String status = "verify";
    @ManyToMany
    @JoinTable(name = "event_category",
            joinColumns = @JoinColumn(name = "eventId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId"))

    private Set<Category> categories;

    public Event(EventBody eventBody) {
        this.eventName = eventBody.getEventName();
        this.eventDescription = eventBody.getEventDescription();
        this.userEntity = eventBody.getUserEntity();
        this.categories = eventBody.getCategorySet();
        this.latitude = eventBody.getLatitude();
        this.longitude = eventBody.getLongitude();
        this.startEvent = eventBody.getStartEvent();
        this.endEvent = eventBody.getEndEvent();
        this.userRating = eventBody.getUserRating();
        this.creationDate = System.currentTimeMillis();
        this.location = eventBody.getLocation();
    }
}
