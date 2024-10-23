package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final EventRoute eventRoute = new EventRoute();
    private final EventGroupRoute eventGroupRoute = new EventGroupRoute();
    private final UserRoute userRoute = new UserRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/event", eventRoute.getRoutes());
                path("/eventgroup", eventGroupRoute.getRoutes());
                path("/user", userRoute.getRoutes());
        };
    }
}
