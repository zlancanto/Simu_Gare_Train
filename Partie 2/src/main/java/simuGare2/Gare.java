package simuGare2;

import database.InMemoryDatabase;
import java.util.Random;

import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.Application;

import application.MyGareApplication;

public class Gare 
{
	private final static int NOMBRE_MAX_VOIES = 9;
	private final static int NOMBRE_MAX_BILLETS = 1000;
	private final static int NOMBRE_MAX_TRAINS = 15;
	private final static int NOMBRE_MAX_VOYAGEURS = 1500;
	private final static int CAPACITE_MAX = 100;
	private final static InMemoryDatabase db = InMemoryDatabase.getInstance();
	
    public static void main(String[] args) throws Exception
    {
    	Component component = new Component();
    	Context context = component.getContext().createChildContext();
    	component.getServers().add(Protocol.HTTP, 8124);
    	
    	Application application = new MyGareApplication(context);
    	
    	context.getAttributes().put("database", db);
    	component.getDefaultHost().attach(application);
    	component.start();
    	
    	int nbPlaceTot = 0;
    	Random rand = new Random();
    	int nombre = rand.nextInt(NOMBRE_MAX_VOIES) + 1;
    	EspaceQuai.getInstance(nombre);
    	EspaceVente.getInstance(NOMBRE_MAX_BILLETS);
    	System.out.println(nombre + " voies.");
    	// on créé les trains
    	int nombreTrain = rand.nextInt(NOMBRE_MAX_TRAINS) + 1 ;
    	int nombreVoyageurs = rand.nextInt(NOMBRE_MAX_VOYAGEURS);

    	System.out.println(nombreTrain + " trains.");
		System.out.println(nombreVoyageurs + " voyageurs.");
		
		for(int i = 0; i < nombreTrain; i ++) 
		{
			int nbPlaces = rand.nextInt(CAPACITE_MAX) + 1;
			Train train = db.createTrain(nbPlaces);
	    	nbPlaceTot += train.getNbPlaces();
	    	train.start();
	    }
	    System.out.println("TotPlaces : " + nbPlaceTot);
	      
	      for(int i = 0; i < nombreVoyageurs; i++) {
	    	  Voyageur voyageur = db.createVoyageur();
	    	  voyageur.setDaemon(true);
	    	  voyageur.start();
	      }
	}
}