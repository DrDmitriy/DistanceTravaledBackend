package backend.controller.requestbody;

import backend.entity.Category;
import backend.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
// @AllArgsConstructor
@Data
// @Builder
public class EventBody {
    private Long userId;
    private String eventName;
    private String eventDescription;
    private UserEntity userEntity;
    private String category;
    private Set<Category> categorySet;
    private Double latitude;
    private Double longitude;
    private String location;
    private Long startEvent;
    private Long endEvent;
    private Double userRating;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public EventBody(
            @JsonProperty("userId") Long userId,
            @JsonProperty("eventName") String eventName,
            @JsonProperty("eventDescription") String eventDescription,
            // @JsonProperty("category") String category,
            @JsonProperty("categories") Set<Category> categorySet,
            @JsonProperty("latitude") Double latitude,
            @JsonProperty("longitude") Double longitude,
            @JsonProperty("location") String location,
            @JsonProperty("startEvent") Timestamp startEvent,
            @JsonProperty("endEvent") Timestamp endEvent) {
        this.userId = userId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        //this.category = category;
        this.categorySet = categorySet;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.startEvent = startEvent.getTime();
        this.endEvent = endEvent.getTime();
    }

}
/*
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
    }*/
