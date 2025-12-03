package ressources;

import org.json.JSONArray;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import database.InMemoryDatabase;

public class TrainRessource extends ServerResource 
{
	private InMemoryDatabase db;

	public TrainRessource()
	{
		super();
		db = (InMemoryDatabase) getApplication().getContext().getAttributes().get("database");
	}
	
	@Get("json")
	public Representation getTrains() throws JSONException
	{
		JSONArray jsonArray = new JSONArray();
		return new JsonRepresentation(jsonArray);
	}
	
	@Post("json")
	public Representation createTrain() throws JSONException
	{
		JSONArray jsonArray = new JSONArray();
		return new JsonRepresentation(jsonArray);
	} 
}
