package ressources;

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
import simuGare2.Voyageur;

public class VoyageurRessource extends ServerResource 
{
	private InMemoryDatabase db;

	public VoyageurRessource()
	{
		super();
		db = (InMemoryDatabase) getApplication().getContext().getAttributes().get("database");
	}
	
	@Get("json")
	public Representation getVoyageurs() throws JSONException
	{
		Collection<Voyageur> voyageurs = db.getVoyageurs();
        Collection<JSONObject> jsonVoyageurs = new ArrayList<JSONObject>();

        for (Voyageur voyageur : voyageurs)
        {
            JSONObject current = new JSONObject();
            current.put("id", voyageur.getIdentifiant());
            current.put("état", voyageur.getState());
            jsonVoyageurs.add(current);

        }
		JSONArray jsonArray = new JSONArray(jsonVoyageurs);
		return new JsonRepresentation(jsonArray);
	}
	
	@Post("json")
	public Representation createVoyageur() throws JSONException
	{
        Voyageur voyageur = db.createVoyageur();
        voyageur.setDaemon(true);
        voyageur.start();
        JSONObject response = new JSONObject();
        response.put("message", "Création de voyageur réussie");

        return new JsonRepresentation(response);
	} 
}
