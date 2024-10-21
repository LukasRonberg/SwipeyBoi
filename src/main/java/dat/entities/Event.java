package dat.entities;

import dat.dtos.EventDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false, unique = true)
    private Integer id;

    @Setter
    @Column(name = "event_name", nullable = false, unique = true)
    private String eventName;

    @Setter
    @Column(name = "estimated_price", nullable = false)
    private double estimatedPrice;

    @Setter
    @Column(name = "description", nullable = false)
    private String description;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "dress_code", nullable = false)
    private DressCode dressCode;

    @OneToMany(mappedBy = "eventgroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<EventGroup> eventGroups = new HashSet<>();

    /*public Event(String hotelName, String hotelAddress, HotelType hotelType) {
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelType = hotelType;
    }*/

    public Event(EventDTO eventDTO) {
        this.id = eventDTO.getId();
        this.eventName = eventDTO.getEventName();
        this.estimatedPrice = eventDTO.getEstimatedPrice();
        this.description = eventDTO.getDescription();
        this.dressCode = eventDTO.getDressCode();
        if (eventDTO.getEventGroups() != null) {
            eventDTO.getEventGroups().forEach(eventGroupDTO -> eventGroups.add(new EventGroup(eventGroupDTO)));
        }
    }

    // Bi-directional relationship for all rooms in a hotel
    public void setEventGroups(Set<EventGroup> eventGroups) {
        if(eventGroups != null) {
            this.eventGroups = eventGroups;
            for (EventGroup eventGroup : eventGroups) {
                eventGroup.setEvent(this);
            }
        }
    }

    // Bi-directional relationship
    public void addEventGroup(EventGroup eventGroup) {
        if ( eventGroup != null) {
            this.eventGroups.add(eventGroup);
            eventGroup.setEvent(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventName, event.eventName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName);
    }

    public enum DressCode {
        BUDGET, STANDARD, LUXURY
    }
}
