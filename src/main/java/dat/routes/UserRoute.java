package dat.routes;

import dat.controllers.impl.UserController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserRoute {

    private final UserController userController = new UserController();

    protected EndpointGroup getRoutes() {

        return () -> {
            //post("/", userController::create, Role.USER);
            get("/", userController::readAll);
            get("/{id}", userController::read);
            put("/{id}", userController::update);
            delete("/{id}", userController::delete);
        };
    }
}
