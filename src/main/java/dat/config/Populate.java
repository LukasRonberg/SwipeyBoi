package dat.config;


import dat.entities.Event;
import dat.entities.EventGroup;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        Set<EventGroup> calEventGroups = getCalRooms();
        Set<EventGroup> hilEventGroups = getHilRooms();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Event california = new Event("Skydive",2000,"Nice sky diving",Event.DressCode.BUDGET);
            Event hilton = new Event("private party",200,"Nice party",Event.DressCode.LUXURY);
            california.setEventGroups(calEventGroups);
            hilton.setEventGroups(hilEventGroups);
            em.persist(california);
            em.persist(hilton);
            em.getTransaction().commit();
        }
    }

    @NotNull
    private static Set<EventGroup> getCalRooms() {
        EventGroup r100 = new EventGroup(100, new BigDecimal(2520), EventGroup.eventgroupType.Adrenalin);
        EventGroup r101 = new EventGroup(101, new BigDecimal(2520), EventGroup.eventgroupType.Adrenalin);
        EventGroup r102 = new EventGroup(102, new BigDecimal(2520), EventGroup.eventgroupType.Adrenalin);
        EventGroup r103 = new EventGroup(103, new BigDecimal(2520), EventGroup.eventgroupType.Adrenalin);
        EventGroup r104 = new EventGroup(104, new BigDecimal(3200), EventGroup.eventgroupType.Chill);
        EventGroup r105 = new EventGroup(105, new BigDecimal(4500), EventGroup.eventgroupType.Alcohol);

        EventGroup[] eventGroupArray = {r100, r101, r102, r103, r104, r105};
        return Set.of(eventGroupArray);
    }

    @NotNull
    private static Set<EventGroup> getHilRooms() {
        EventGroup r111 = new EventGroup(111, new BigDecimal(2520), EventGroup.eventgroupType.Adrenalin);
        EventGroup r112 = new EventGroup(112, new BigDecimal(2520), EventGroup.eventgroupType.Adrenalin);
        EventGroup r113 = new EventGroup(113, new BigDecimal(2520), EventGroup.eventgroupType.Adrenalin);
        EventGroup r114 = new EventGroup(114, new BigDecimal(2520), EventGroup.eventgroupType.Chill);
        EventGroup r115 = new EventGroup(115, new BigDecimal(3200), EventGroup.eventgroupType.Chill);
        EventGroup r116 = new EventGroup(116, new BigDecimal(4500), EventGroup.eventgroupType.Alcohol);

        EventGroup[] eventGroupArray = {r111, r112, r113, r114, r115, r116};
        return Set.of(eventGroupArray);
    }
}
