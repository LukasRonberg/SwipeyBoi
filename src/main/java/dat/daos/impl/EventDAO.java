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
            Event h = em.find(Event.class, integer);
            h.setHotelName(eventDTO.getEventName());
            h.setHotelAddress(eventDTO.getDescription());
            h.setHotelType(eventDTO.getDressCode());
            Event mergedEvent = em.merge(h);
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
}
