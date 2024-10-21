package dat.routes;

import dat.controllers.impl.EventController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EventRoute {

    private final EventController eventController = new EventController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", eventController::create, Role.USER);
            get("/", eventController::readAll);
            get("/{id}", eventController::read);
            put("/{id}", eventController::update);
            delete("/{id}", eventController::delete);
        };
    }
}
