package dat.routes;

import dat.controllers.impl.EventGroupController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EventGroupRoute {

    private final EventGroupController eventGroupController = new EventGroupController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/event/{id}", eventGroupController::create);
            get("/", eventGroupController::readAll);
            get("/{id}", eventGroupController::read);
            put("/{id}", eventGroupController::update);
            delete("/{id}", eventGroupController::delete);
        };
    }
}
