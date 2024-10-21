package dat.entities;

import dat.dtos.EventGroupDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
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
    private BigDecimal EventGroupPrice;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "eventgroup_type", nullable = false)
    private eventgroupType EventGroupType;

    @Setter
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public EventGroup(Integer eventgroupNumber, BigDecimal eventgroupPrice, eventgroupType EventGroupType) {
        this.EventGroupNumber = eventgroupNumber;
        this.EventGroupPrice = eventgroupPrice;
        this.EventGroupType = EventGroupType;
    }

    public EventGroup(EventGroupDTO eventgroupDTO){
        this.EventGroupId = eventgroupDTO.getId();
        this.EventGroupNumber = eventgroupDTO.getEventGroupNumber();
        this.EventGroupPrice = BigDecimal.valueOf(eventgroupDTO.getEventGroupPrice());
        this.EventGroupType = eventgroupDTO.getEventGroupType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventGroup eventGroup = (EventGroup) o;
        return Objects.equals(EventGroupNumber, eventGroup.EventGroupNumber) && Objects.equals(hotel, eventGroup.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(EventGroupNumber, hotel);
    }

    public enum eventgroupType {
        SINGLE, DOUBLE, SUITE
    }
}
