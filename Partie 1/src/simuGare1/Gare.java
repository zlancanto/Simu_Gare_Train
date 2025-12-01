package simuGare1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gare 
{
	private final static int NOMBRE_MAX_VOIES = 9;
	private final static int NOMBRE_MAX_BILLETS = 1000;
	private final static int NOMBRE_MAX_TRAINS = 15;
	private final static int NOMBRE_MAX_VOYAGEURS = 1500;
	private static EspaceQuai espaceQuai;
	private static EspaceVente espaceVente;
	//private static List<Train> trains = new ArrayList<>();
	private static List<Voyageur> voyageurs = new ArrayList<>();
	
    public static void main(String[] args) 
    {
    	  Random rand = new Random();
          int nombre = rand.nextInt(NOMBRE_MAX_VOIES) + 1;
          espaceQuai = EspaceQuai.getInstance(nombre);
          espaceVente = EspaceVente.getInstance(NOMBRE_MAX_BILLETS);
          
          // on créé les trains
          int nombreTrain = rand.nextInt(NOMBRE_MAX_TRAINS);
          for(int i = 0; i < nombreTrain; i ++) {
        	  Train train = new Train();
        	  train.start();
          }
          
          int nombreVoyageurs = rand.nextInt(NOMBRE_MAX_VOYAGEURS);
          for(int i = 0; i < nombreVoyageurs; i++) {
        	  Voyageur voyageur = new Voyageur();
        	  voyageur.setDaemon(true);
        	  voyageur.start();
          }
    }
}