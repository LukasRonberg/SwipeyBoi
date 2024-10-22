package dat.config;


import dat.entities.Event;
import dat.entities.EventGroup;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        Set<EventGroup> skydiveEventGroups = getSkydiveEventGroups();
        Set<EventGroup> hikeEventGroups = getHikeEventGroups();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Event skydiving = new Event("Skydiving",2000,"Nice sky diving",Event.DressCode.BUDGET);
            Event hiking = new Event("Hiking",50,"A great hike with a great view",Event.DressCode.LUXURY);
            skydiving.setEventGroups(skydiveEventGroups);
            hiking.setEventGroups(hikeEventGroups);
            em.persist(skydiving);
            em.persist(hiking);
            em.getTransaction().commit();
        }
    }

    @NotNull
    private static Set<EventGroup> getSkydiveEventGroups() {
        EventGroup r100 = new EventGroup(100, 2520.0, EventGroup.eventgroupType.Adrenalin, LocalDate.of(2024, 1, 1), LocalTime.of(10, 0));
        EventGroup r101 = new EventGroup(101, 2520.0, EventGroup.eventgroupType.Adrenalin, LocalDate.of(2024, 1, 2), LocalTime.of(11, 0));
        EventGroup r102 = new EventGroup(102, 2520.0, EventGroup.eventgroupType.Adrenalin, LocalDate.of(2024, 1, 3), LocalTime.of(12, 0));
        EventGroup r103 = new EventGroup(103, 2520.0, EventGroup.eventgroupType.Adrenalin, LocalDate.of(2024, 1, 4), LocalTime.of(13, 0));
        EventGroup r104 = new EventGroup(104, 3200.0, EventGroup.eventgroupType.Chill, LocalDate.of(2024, 1, 5), LocalTime.of(14, 0));
        EventGroup r105 = new EventGroup(105, 4500.0, EventGroup.eventgroupType.Alcohol, LocalDate.of(2024, 1, 6), LocalTime.of(15, 0));

        EventGroup[] eventGroupArray = {r100, r101, r102, r103, r104, r105};
        return Set.of(eventGroupArray);
    }

    @NotNull
    private static Set<EventGroup> getHikeEventGroups() {
        EventGroup r111 = new EventGroup(111, 2520.0, EventGroup.eventgroupType.Adrenalin, LocalDate.of(2024, 2, 1), LocalTime.of(10, 0));
        EventGroup r112 = new EventGroup(112, 2520.0, EventGroup.eventgroupType.Adrenalin, LocalDate.of(2024, 2, 2), LocalTime.of(11, 0));
        EventGroup r113 = new EventGroup(113, 2520.0, EventGroup.eventgroupType.Adrenalin, LocalDate.of(2024, 2, 3), LocalTime.of(12, 0));
        EventGroup r114 = new EventGroup(114, 2520.0, EventGroup.eventgroupType.Chill, LocalDate.of(2024, 2, 4), LocalTime.of(13, 0));
        EventGroup r115 = new EventGroup(115, 3200.0, EventGroup.eventgroupType.Chill, LocalDate.of(2024, 2, 5), LocalTime.of(14, 0));
        EventGroup r116 = new EventGroup(116, 4500.0, EventGroup.eventgroupType.Alcohol, LocalDate.of(2024, 2, 6), LocalTime.of(15, 0));

        EventGroup[] eventGroupArray = {r111, r112, r113, r114, r115, r116};
        return Set.of(eventGroupArray);
    }

}
