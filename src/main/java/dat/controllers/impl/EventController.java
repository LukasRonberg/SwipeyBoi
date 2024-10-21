package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.EventDAO;
import dat.dtos.EventDTO;
import dat.entities.Event;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class EventController implements IController<EventDTO, Integer> {

    private final EventDAO dao;

    public EventController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = EventDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx)  {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        EventDTO eventDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(eventDTO, EventDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<EventDTO> eventDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(eventDTOS, EventDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        EventDTO jsonRequest = ctx.bodyAsClass(EventDTO.class);
        // DTO
        EventDTO eventDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(eventDTO, EventDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        EventDTO eventDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(eventDTO, Event.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public EventDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(EventDTO.class)
                .check( h -> h.getDescription() != null && !h.getDescription().isEmpty(), "Hotel address must be set")
                .check( h -> h.getEventName() != null && !h.getEventName().isEmpty(), "Hotel name must be set")
                .check( h -> h.getDressCode() != null, "Hotel type must be set")
                .get();
    }

}
