package backend.entity;

import backend.controller.requestbody.EventBody;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"categories","userEntity"})
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
  //  @JsonIgnore
    private Set<Category> categories = new HashSet<>();

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

   public Set<Category> getCategories() {
        return categories;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity user) {
        this.userEntity = user;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getStartEvent() {
        return startEvent;
    }

    public void setStartEvent(Long startEvent) {
        this.startEvent = startEvent;
    }

    public Long getEndEvent() {
        return endEvent;
    }

    public void setEndEvent(Long endEvent) {
        this.endEvent = endEvent;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;

    }



    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventDiscription='" + eventDescription + '\'' +
                ", user=" + userEntity +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", location='" + location + '\'' +
                ", startEvent=" + startEvent +
                ", endEvent=" + endEvent +
                ", userRating=" + userRating +
                '}';
    }

}
