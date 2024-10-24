package dat.routes;

import dat.controllers.impl.EventController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EventRoute {

    private final EventController eventController = new EventController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", eventController::create, Role.ADMIN);
            get("/", eventController::readAll, Role.USER);
            get("/{id}", eventController::read, Role.USER);
            put("/{id}", eventController::update, Role.ADMIN);
            delete("/{id}", eventController::delete, Role.ADMIN);
        };
    }
}
