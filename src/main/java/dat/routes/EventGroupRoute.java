package dat.routes;

import dat.controllers.impl.EventGroupController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EventGroupRoute {

    private final EventGroupController eventGroupController = new EventGroupController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/event/{id}", eventGroupController::create, Role.ADMIN);
            get("/", eventGroupController::readAll, Role.USER);
            get("/{id}", eventGroupController::read, Role.USER);
            put("/{id}", eventGroupController::update, Role.ADMIN);
            delete("/{id}", eventGroupController::delete, Role.ADMIN);
        };
    }
}
