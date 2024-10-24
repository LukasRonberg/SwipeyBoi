package dat.dtos;

import dat.entities.EventGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class EventGroupDTO {
    private Integer Id;
    private Integer EventGroupNumber;
    private Double EventGroupPrice;
    private LocalDate eventDate;
    private LocalTime eventTime;

    public EventGroupDTO(EventGroup EventGroup) {
        this.Id = EventGroup.getEventGroupId();
        this.EventGroupNumber = EventGroup.getEventGroupNumber();
        this.EventGroupPrice = EventGroup.getEventGroupPrice();
        this.eventDate = EventGroup.getEventDate();
        this.eventTime = EventGroup.getEventTime();
    }

    public static List<EventGroupDTO> toEventGroupDTOList(List<EventGroup> EventGroups) {
        return List.of(EventGroups.stream().map(EventGroupDTO::new).toArray(EventGroupDTO[]::new));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        EventGroupDTO EventGroupDTO = (EventGroupDTO) o;
        return getId().equals(EventGroupDTO.getId()) && getEventGroupNumber().equals(EventGroupDTO.getEventGroupNumber()) && getEventGroupPrice().equals(EventGroupDTO.getEventGroupPrice());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getEventGroupNumber().hashCode();
        result = 31 * result + getEventGroupPrice().hashCode();
        return result;
    }
}
