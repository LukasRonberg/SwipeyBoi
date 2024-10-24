package dat.entities;

import dat.dtos.EventGroupDTO;
import dat.security.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "eventgroup")
public class EventGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventgroup_id", nullable = false, unique = true)
    private Integer EventGroupId;

    @Setter
    @Column(name = "eventgroup_number", nullable = false)
    private Integer EventGroupNumber;

    @Setter
    @Column(name = "price", nullable = false)
    private double EventGroupPrice;

    @Setter
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Setter
    @Column(name = "event_time", nullable = false)
    private LocalTime eventTime;

    @Setter
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToMany(mappedBy = "eventGroups")
    private Set<User> users = new HashSet<>();

    public EventGroup(Integer eventGroupNumber, double eventGroupPrice) {
        this.EventGroupNumber = eventGroupNumber;
        this.EventGroupPrice = eventGroupPrice;
    }

    public EventGroup(Integer eventGroupNumber, double eventGroupPrice, LocalDate eventDate, LocalTime eventTime) {
        this.EventGroupNumber = eventGroupNumber;
        this.EventGroupPrice = eventGroupPrice;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    public EventGroup(EventGroupDTO eventgroupDTO){
        this.EventGroupId = eventgroupDTO.getId();
        this.EventGroupNumber = eventgroupDTO.getEventGroupNumber();
        this.EventGroupPrice = eventgroupDTO.getEventGroupPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventGroup eventGroup = (EventGroup) o;
        return Objects.equals(EventGroupNumber, eventGroup.EventGroupNumber) && Objects.equals(event, eventGroup.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(EventGroupNumber, event);
    }



}
