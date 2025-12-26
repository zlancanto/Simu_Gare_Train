package ressources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import database.InMemoryDatabase;
import simuGare2.Train;
import simuGare2.Voyageur;

public class TrainRessource extends ServerResource 
{
	private InMemoryDatabase db;

	public TrainRessource()
	{
		super();
		db = (InMemoryDatabase) getApplication()
                .getContext()
                .getAttributes()
                .get("database");
	}
	
	@Get("json")
	public Representation getTrains() throws JSONException
	{
		Collection<Train> trains = db.getTrains();
        Collection<JSONObject> jsonTrain = new ArrayList<>();

        for (Train train : trains)
        {
            JSONObject current = new JSONObject();
            current.put("id", train.getIdentifiant());
            current.put("nbPlaces", train.getNbPlaces());
            current.put("état", train.getTrainState());
            jsonTrain.add(current);

        }
		JSONArray jsonArray = new JSONArray(jsonTrain);
		return new JsonRepresentation(jsonArray);
	}
	
	@Post("json")
	public Representation createTrain(Representation entity) throws JSONException, IOException {

	    JSONObject json = new JSONObject(entity.getText());
	    int nbPlaces = json.getInt("nbPlaces");

	    Train train = db.createTrain(nbPlaces);
	    train.start();

	    JSONObject response = new JSONObject();
	    response.put("message", "Création de train réussie");

	    return new JsonRepresentation(response);
	}
}
