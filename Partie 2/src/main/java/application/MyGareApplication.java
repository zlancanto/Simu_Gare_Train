package application;

import ressources.*;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 *
 * Application.
 *
 * @author mihan bot
 *
 */
public class MyGareApplication extends Application
{

    public MyGareApplication(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot()
    {
        Router router = new Router(getContext());
        router.attach("/trains", TrainRessource.class);
        router.attach("/voyageurs", VoyageurRessource.class);
        return router;
    }
}
