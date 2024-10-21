package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final EventRoute eventRoute = new EventRoute();
    private final RoomRoute roomRoute = new RoomRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/hotels", eventRoute.getRoutes());
                path("/rooms", roomRoute.getRoutes());
        };
    }
}
