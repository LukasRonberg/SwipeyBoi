package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.dtos.EventGroupDTO;
import dat.entities.Event;
import dat.entities.EventGroup;
import org.junit.jupiter.api.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventGroupDAOtest {

    private static EntityManagerFactory emf;
    private EventGroupDAO eventGroupDAO;

    @BeforeAll
    public void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest(); // "test-pu" must be defined in persistence.xml
        eventGroupDAO = EventGroupDAO.getInstance(emf);

        // Prepare test data
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Create a test event
            Event event = new Event();
            event.setEventName("Test Event");
            event.setDescription("Test Description");
            event.setEstimatedPrice(1000);
            event.setEventType(Event.EventType.Chill);
            event.setDressCode(Event.DressCode.BUDGET);

            // Create a test event group
            EventGroup eventGroup = new EventGroup();
            eventGroup.setEventDate(LocalDate.of(2022, 1, 1));
            eventGroup.setEventTime(LocalTime.of(12, 0));
            eventGroup.setEventGroupNumber(1);
            eventGroup.setEventGroupPrice(100);
            eventGroup.setEvent(event);

            // Create a test event
            Event event2 = new Event();
            event2.setEventName("Test Event2");
            event2.setDescription("Test Description");
            event2.setEstimatedPrice(1000);
            event2.setEventType(Event.EventType.Chill);
            event2.setDressCode(Event.DressCode.BUDGET);

            // Create a test event group
            EventGroup eventGroup2 = new EventGroup();
            eventGroup2.setEventDate(LocalDate.of(2022, 1, 1));
            eventGroup2.setEventTime(LocalTime.of(12, 0));
            eventGroup2.setEventGroupNumber(2);
            eventGroup2.setEventGroupPrice(100);
            eventGroup2.setEvent(event2);


            // Associate the event group with the event
            event.addEventGroup(eventGroup);
            em.persist(event);
            event2.addEventGroup(eventGroup2);
            em.persist(event2);
            em.getTransaction().commit();
        }
    }

/*
    @Test
    public void testCreateEventGroup() {
        EventGroupDTO eventGroupDTO = new EventGroupDTO();
        eventGroupDTO.setEventGroupNumber(2);
        eventGroupDTO.setEventGroupPrice(200.0);
        EventGroupDTO createdEventGroup = eventGroupDAO.create(eventGroupDTO);

        assertNotNull(createdEventGroup);
        assertEquals(2, createdEventGroup.getEventGroupNumber());
        assertEquals(200.0, createdEventGroup.getEventGroupPrice());
    }*/

    @Test
    public void testReadEventGroup() {
        EventGroupDTO eventGroup = eventGroupDAO.read(1);
        assertNotNull(eventGroup);
        assertEquals(1, eventGroup.getEventGroupNumber());
    }

    @Test
    public void testUpdateEventGroup() {
        EventGroupDTO eventGroupDTO = new EventGroupDTO();
        eventGroupDTO.setEventGroupNumber(1);
        eventGroupDTO.setEventGroupPrice(150.0);

        EventGroupDTO updatedEventGroup = eventGroupDAO.update(1, eventGroupDTO);
        assertNotNull(updatedEventGroup);
        assertEquals(150.0, updatedEventGroup.getEventGroupPrice());
    }

    @Test
    public void testDeleteEventGroup() {
        EventGroupDTO eventGroup = eventGroupDAO.read(2);
        assertNotNull(eventGroup);
        eventGroupDAO.delete(2);
        eventGroup = eventGroupDAO.read(2);
        assertNull(eventGroup);
    }

    @Test
    public void testReadAllEventGroups() {
        List<EventGroupDTO> eventGroups = eventGroupDAO.readAll();
        assertNotNull(eventGroups);
        assertFalse(eventGroups.isEmpty());
    }

    @Test
    public void testValidatePrimaryKey() {
        boolean exists = eventGroupDAO.validatePrimaryKey(1);
        assertTrue(exists);

        boolean nonExistent = eventGroupDAO.validatePrimaryKey(999);
        assertFalse(nonExistent);
    }

    @AfterAll
    public void tearDown() {
        if (emf != null) {
            emf.close();
        }
    }
}
