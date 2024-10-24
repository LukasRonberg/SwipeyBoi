package dat.daos.impl;


import dat.daos.IDAO;
import dat.dtos.EventDTO;
import dat.dtos.EventGroupDTO;
import dat.entities.EventGroup;
import dat.entities.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EventGroupDAO implements IDAO<EventGroupDTO, Integer> {

    private static EventGroupDAO instance;
    private static EntityManagerFactory emf;

    public static EventGroupDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EventGroupDAO();
        }
        return instance;
    }

    public EventDTO addRoomToHotel(Integer eventId, EventGroupDTO eventGroupDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            EventGroup eventGroup = new EventGroup(eventGroupDTO);
            Event event = em.find(Event.class, eventId);
            event.addEventGroup(eventGroup);
            em.persist(eventGroup);
            Event mergedHotel = em.merge(event);
            em.getTransaction().commit();
            return new EventDTO(mergedHotel);
        }
    }

    @Override
    public EventGroupDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            EventGroup eventGroup = em.find(EventGroup.class, integer);
            return eventGroup != null ? new EventGroupDTO(eventGroup) : null;
        }
    }

    @Override
    public List<EventGroupDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<EventGroupDTO> query = em.createQuery("SELECT new dat.dtos.EventGroupDTO(r) FROM EventGroup r", EventGroupDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public EventGroupDTO create(EventGroupDTO eventGroupDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            EventGroup eventGroup = new EventGroup(eventGroupDTO);
            em.persist(eventGroup);
            em.getTransaction().commit();
            return new EventGroupDTO(eventGroup);
        }
    }

    @Override
    public EventGroupDTO update(Integer integer, EventGroupDTO eventGroupDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            EventGroup r = em.find(EventGroup.class, integer);
            r.setEventGroupNumber(eventGroupDTO.getEventGroupNumber());
            //r.setEventGroupType(eventGroupDTO.getEventGroupType());
            r.setEventGroupPrice(eventGroupDTO.getEventGroupPrice());
            EventGroup mergedEventGroup = em.merge(r);
            em.getTransaction().commit();
            return new EventGroupDTO(mergedEventGroup);
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            EventGroup eventGroup = em.find(EventGroup.class, integer);

            if (eventGroup != null) {
                // First, remove the association with the parent Event
                Event event = eventGroup.getEvent(); // Assuming you have a `getEvent()` method in EventGroup entity
                if (event != null) {
                    event.getEventGroups().remove(eventGroup);
                    em.merge(event); // Update the Event to reflect the removal of the EventGroup
                }
                // Now you can safely remove the EventGroup
                em.remove(eventGroup);
            }

            em.getTransaction().commit();
        }
    }


    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            EventGroup eventGroup = em.find(EventGroup.class, integer);
            return eventGroup != null;
        }
    }

    public Function<Integer, Boolean> validateHotelRoomNumber = (roomNumber) -> {
        try (EntityManager em = emf.createEntityManager()) {
            EventGroup eventGroup = em.find(EventGroup.class, roomNumber);
            return eventGroup != null;
        }
    };

    public Boolean validateEventGroupNumber(Integer eventGroupNumber, Integer hotelId) {
        try (EntityManager em = emf.createEntityManager()) {
            Event event = em.find(Event.class, hotelId);
            return event.getEventGroups().stream().anyMatch(r -> r.getEventGroupNumber().equals(eventGroupNumber));
        }
    }

}
