package simuGare1;

import java.util.Random;

public class Gare 
{
	private final static int NOMBRE_MAX_VOIES = 9;
	private final static int NOMBRE_MAX_BILLETS = 1000;
	private final static int NOMBRE_MAX_TRAINS = 15;
	private final static int NOMBRE_MAX_VOYAGEURS = 1500;
	
    public static void main(String[] args) 
    {
    	int nbPlaceTot = 0;
    	Random rand = new Random();
    	int nombreVoies = rand.nextInt(NOMBRE_MAX_VOIES) + 1;
    	EspaceQuai.getInstance(nombreVoies);
    	EspaceVente.getInstance(NOMBRE_MAX_BILLETS);
    	System.out.println(nombreVoies + " voies.");
    	// on créé les trains
    	int nombreTrain = rand.nextInt(NOMBRE_MAX_TRAINS) + 1 ;
    	int nombreVoyageurs = rand.nextInt(NOMBRE_MAX_VOYAGEURS);

    	System.out.println(nombreTrain + " trains.");
		System.out.println(nombreVoyageurs + " voyageurs.");
		
		for(int i = 0; i < nombreTrain; i ++) 
		{
			Train train = new Train();
	    	nbPlaceTot += train.getNbPlaces();
	    	train.start();
	    }
	    System.out.println("TotPlaces : " + nbPlaceTot);
	      
        for(int i = 0; i < nombreVoyageurs; i++)
        {
          Voyageur voyageur = new Voyageur();
          voyageur.setDaemon(true);
          voyageur.start();
        }
	}
}