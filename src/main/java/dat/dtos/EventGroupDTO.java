package dat.dtos;

import dat.entities.EventGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class EventGroupDTO {
    private Integer Id;
    private Integer EventGroupNumber;
    private Integer EventGroupPrice;
    private EventGroup.eventgroupType EventGroupType;

    public EventGroupDTO(EventGroup EventGroup) {
        this.Id = EventGroup.getEventGroupId();
        this.EventGroupNumber = EventGroup.getEventGroupNumber();
        this.EventGroupPrice = EventGroup.getEventGroupPrice().intValue();
        this.EventGroupType = EventGroup.getEventGroupType();
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
        return getId().equals(EventGroupDTO.getId()) && getEventGroupNumber().equals(EventGroupDTO.getEventGroupNumber()) && getEventGroupPrice().equals(EventGroupDTO.getEventGroupPrice()) && getEventGroupType() == EventGroupDTO.getEventGroupType();
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getEventGroupNumber().hashCode();
        result = 31 * result + getEventGroupPrice().hashCode();
        result = 31 * result + getEventGroupType().hashCode();
        return result;
    }
}