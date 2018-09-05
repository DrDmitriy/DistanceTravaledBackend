package backend.controller.requestbody;

import backend.entity.Category;
import backend.entity.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class EventBody {
    private Long userId;
    private String eventName;
    private String eventDiscription;
    private User user;
    private String category;
    private Set<Category> categorySet;
    private Double latitude;
    private Double longitude;
    private String location;
    private Timestamp startEvent;
    private Timestamp endEvent;
    private Double userRating;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public EventBody(
            @JsonProperty("userId") Long userId,
            @JsonProperty("eventName") String eventName,
            @JsonProperty("eventDiscription") String eventDiscription,
            @JsonProperty("category") String category,
            @JsonProperty("latitude") Double latitude,
            @JsonProperty("longitude") Double longitude,
            @JsonProperty("location") String location,
            @JsonProperty("startEvent") String startEvent,
            @JsonProperty("endEvent") String endEvent) {
        this.userId = userId;
        this.eventName = eventName;
        this.eventDiscription = eventDiscription;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.startEvent = stringDateToTimestamp(startEvent);
        this.endEvent = stringDateToTimestamp(endEvent);
    }

    private Timestamp stringDateToTimestamp(String startEvent) {
        String[] date = startEvent.split("\\.");
        if (date != null && date.length == 5) {
            int year = Integer.valueOf(date[0]);
            int month = Integer.valueOf(date[1]);
            int day = Integer.valueOf(date[2]);
            int hour = Integer.valueOf(date[3]);
            int min = Integer.valueOf(date[4]);
            return new Timestamp(year - 1900, month - 1, day, hour, min, 0, 0);
        } else {
            return null;
        }
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public void addCategory(Category category) {
        if (this.categorySet == null) {
            this.categorySet = new HashSet<>();
        }
        this.categorySet.add(category);
    }

    public Long getuserId() {
        return userId;
    }

    public void setuserId(Long userId) {
        this.userId = userId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDiscription() {
        return eventDiscription;
    }

    public void setEventDiscription(String eventDiscription) {
        this.eventDiscription = eventDiscription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Timestamp getStartEvent() {
        return startEvent;
    }

    public void setStartEvent(Timestamp startEvent) {
        this.startEvent = startEvent;
    }

    public Timestamp getEndEvent() {
        return endEvent;
    }

    public void setEndEvent(Timestamp endEvent) {
        this.endEvent = endEvent;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }
}
