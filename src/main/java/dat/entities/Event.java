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

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<EventGroup> eventGroups = new HashSet<>();

    /*public Event(String hotelName, String hotelAddress, HotelType hotelType) {
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelType = hotelType;
    }*/

    public Event(String eventName, double estimatedPrice, String description, DressCode dressCode, EventType eventType) {
        this.eventName = eventName;
        this.estimatedPrice = estimatedPrice;
        this.description = description;
        this.dressCode = dressCode;
        this.eventType = eventType;
    }

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
    public enum EventType {
        Adrenalin,    // Exciting, high-energy events like extreme sports or adventures
        Chill,        // Relaxed, calm events like yoga or nature retreats
        Alcohol,      // Events where alcohol is a major focus, like bar crawls or wine tastings
        Educational,  // Workshops, seminars, or learning-based events
        Music,        // Concerts, festivals, or music-related gatherings
        Cultural,     // Museum tours, cultural festivals, or heritage-related events
        Social,       // Meetups, networking events, or social gatherings
        Fitness,      // Workout sessions, marathons, or fitness challenges
        Creative,     // Art classes, craft workshops, or creative projects
        Foodie,       // Food-focused events like tastings, cooking classes, or food festivals
        Spiritual,    // Meditation, mindfulness, or religious events
        Outdoor,      // Camping, hiking, or other nature-focused events
        Sports,       // Spectator sports events, or casual sports like soccer, basketball
        Party,        // Nightclubs, rave parties, or large celebratory events
        Tech,         // Hackathons, tech conferences, or innovation workshops
        Charity       // Fundraisers, volunteer activities, or charity events
    }
}
