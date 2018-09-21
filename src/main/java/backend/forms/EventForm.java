package backend.forms;

import lombok.*;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class EventForm {
    private Long id;
    private String label;
    private Long creation;
    private Long start;
    private String startStr;
    private Long end;
    private String endStr;
    private Double lat;
    private Double lng;
    private Set<String> categories;
    private String location;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventForm eventForm = (EventForm) o;
        return Objects.equals(id, eventForm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}