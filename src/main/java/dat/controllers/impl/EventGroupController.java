package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.EventGroupDAO;
import dat.dtos.EventDTO;
import dat.dtos.EventGroupDTO;
import dat.exceptions.Message;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.function.BiFunction;

public class EventGroupController implements IController<EventGroupDTO, Integer> {

    private EventGroupDAO dao;

    public EventGroupController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = EventGroupDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        EventGroupDTO eventGroupDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(eventGroupDTO, EventGroupDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // entity
        List<EventGroupDTO> eventGroupDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(eventGroupDTOS, EventGroupDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        EventGroupDTO jsonRequest = validateEntity(ctx);

        int hotelId = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        Boolean hasRoom = validateHotelRoomNumber.apply(jsonRequest.getEventGroupNumber(), hotelId);

        if (hasRoom) {
            ctx.res().setStatus(400);
            ctx.json(new Message(400, "Room number already in use by hotel"));
            return;
        }

        EventDTO eventDTO = dao.addRoomToHotel(hotelId, jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(eventDTO, EventDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        EventGroupDTO eventGroupDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(eventGroupDTO, EventGroupDTO.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        dao.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {return dao.validatePrimaryKey(integer);}

    // Checks if the room number is already in use by the hotel
    BiFunction<Integer, Integer, Boolean> validateHotelRoomNumber = (roomNumber, hotelId) -> dao.validateEventGroupNumber(roomNumber, hotelId);

    @Override
    public EventGroupDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(EventGroupDTO.class)
                .check(r -> r.getEventGroupNumber() != null && r.getEventGroupNumber() > 0, "Not a valid room number")
                //.check(r -> r.getEventGroupType() != null, "Not a valid room type")
                .check(r -> r.getEventGroupPrice() != null , "Not a valid price")
                .get();
    }
}
