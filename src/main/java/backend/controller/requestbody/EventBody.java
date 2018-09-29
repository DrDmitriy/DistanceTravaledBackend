package backend.controller.requestbody;

import backend.entity.Category;
import backend.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@Data
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
