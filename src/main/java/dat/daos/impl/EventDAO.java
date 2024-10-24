package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.EventDTO;
import dat.entities.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EventDAO implements IDAO<EventDTO, Integer> {

    private static EventDAO instance;
    private static EntityManagerFactory emf;

    public static EventDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EventDAO();
        }
        return instance;
    }

    @Override
    public EventDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Event event = em.find(Event.class, integer);
            return new EventDTO(event);
        }
    }

    @Override
    public List<EventDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<EventDTO> query = em.createQuery("SELECT new dat.dtos.EventDTO(h) FROM Event h", EventDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public EventDTO create(EventDTO eventDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Event event = new Event(eventDTO);
            em.persist(event);
            em.getTransaction().commit();
            return new EventDTO(event);
        }
    }

    @Override
    public EventDTO update(Integer integer, EventDTO eventDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Event e = em.find(Event.class, integer);
            e.setEventName(eventDTO.getEventName());
            e.setDescription(eventDTO.getDescription());
            e.setDressCode(eventDTO.getDressCode());
            e.setEstimatedPrice(eventDTO.getEstimatedPrice());
            Event mergedEvent = em.merge(e);
            em.getTransaction().commit();
            return mergedEvent != null ? new EventDTO(mergedEvent) : null;
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Event event = em.find(Event.class, integer);
            if (event != null) {
                em.remove(event);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Event event = em.find(Event.class, integer);
            return event != null;
        }
    }

    /*public List<EventDTO> getByType(String type) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<EventDTO> query = em.createQuery("SELECT new dat.dtos.EventDTO(h) FROM Event h WHERE h.eventType = :type", EventDTO.class);
            query.setParameter("type", type);
            return query.getResultList();
        }
    }*/

    public List<EventDTO> getByType(String type) {
        try (EntityManager em = emf.createEntityManager()) {
            // Convert the string input to the EventType enum
            Event.EventType eventType;
            try {
                eventType = Event.EventType.valueOf(type);  // Convert string to EventType enum
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid event type: " + type);  // Handle invalid enum value
            }

            // Use the converted EventType enum in the query
            TypedQuery<EventDTO> query = em.createQuery("SELECT new dat.dtos.EventDTO(h) FROM Event h WHERE h.eventType = :type", EventDTO.class);
            query.setParameter("type", eventType);  // Pass enum value to the query
            return query.getResultList();
        }
    }

}
