package dat.config;


import dat.entities.Event;
import dat.entities.EventGroup;
import dat.security.entities.User;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        Set<EventGroup> skydiveEventGroups = getSkydiveEventGroups();
        Set<EventGroup> hikingEventGroups = getHikeEventGroups();
        Set<User> users = getUsers();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Event skydiving = new Event("Skydiving",2000,"Nice sky diving",Event.DressCode.BUDGET, Event.EventType.Adrenalin);
            Event hiking = new Event("Hiking",50,"A great hike with a great view",Event.DressCode.LUXURY, Event.EventType.Fitness);

            skydiving.setEventGroups(skydiveEventGroups);
            hiking.setEventGroups(hikingEventGroups);
            em.persist(skydiving);
            em.persist(hiking);

            users.forEach(user -> {
                skydiveEventGroups.forEach(eventGroup -> {
                    user.addEventGroup(eventGroup);
                    eventGroup.getUsers().add(user);
                });
                hikingEventGroups.forEach(eventGroup -> {
                    user.addEventGroup(eventGroup);
                    eventGroup.getUsers().add(user);
                });

                em.persist(user); // Persist the user
            });

            em.getTransaction().commit();
        }
    }

    @NotNull
    private static Set<EventGroup> getSkydiveEventGroups() {
        EventGroup r100 = new EventGroup(100, 2520.0, LocalDate.of(2024, 1, 1), LocalTime.of(10, 0));
        EventGroup r101 = new EventGroup(101, 2520.0, LocalDate.of(2024, 1, 2), LocalTime.of(11, 0));
        EventGroup r102 = new EventGroup(102, 2520.0, LocalDate.of(2024, 1, 3), LocalTime.of(12, 0));

        EventGroup[] eventGroupArray = {r100, r101, r102};
        return Set.of(eventGroupArray);
    }

    @NotNull
    private static Set<EventGroup> getHikeEventGroups() {
        EventGroup r111 = new EventGroup(111, 2520.0, LocalDate.of(2024, 2, 1), LocalTime.of(10, 0));
        EventGroup r112 = new EventGroup(112, 2520.0, LocalDate.of(2024, 2, 2), LocalTime.of(11, 0));
        EventGroup r113 = new EventGroup(113, 2520.0, LocalDate.of(2024, 2, 3), LocalTime.of(12, 0));

        EventGroup[] eventGroupArray = {r111, r112, r113};
        return Set.of(eventGroupArray);
    }


    private static Set<User> getUsers() {
        User r111 = new User("Bob", "testtest", 12,62748596,"biboi5@fck.ok");
        User r112 = new User("Boblicia", "testtest", 72,62748596,"biboi4@fck.ok");

        User[] UserGroupArray = {r111, r112};
        return Set.of(UserGroupArray);
    }

}
