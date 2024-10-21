package dat.dtos;

import dat.entities.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class EventDTO {

    private Integer id;
    private String eventName;
    private double estimatedPrice;
    private String description;
    private Event.DressCode dressCode;
    private Set<EventGroupDTO> eventGroups = new HashSet<>();

    public EventDTO(Event event) {
        this.id = event.getId();
        this.eventName = event.getEventName();
        this.estimatedPrice = event.getEstimatedPrice();
        this.description = event.getDescription();
        this.dressCode = event.getDressCode();
        if (event.getEventGroups() != null)
        {
            event.getEventGroups().forEach(eventGroup -> eventGroups.add(new EventGroupDTO(eventGroup)));
        }
    }

    public EventDTO(String eventName, String description, Event.DressCode dressCode)
    {
        this.eventName = eventName;
        this.description = description;
        this.dressCode = dressCode;
    }

    public static List<EventDTO> toHotelDTOList(List<Event> events) {
        return events.stream().map(EventDTO::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof EventDTO eventDto)) return false;

        return getId().equals(eventDto.getId());
    }

    @Override
    public int hashCode()
    {
        return getId().hashCode();
    }

}
