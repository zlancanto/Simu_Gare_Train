package simuGare2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@inheritDoc}
 */
public final class EspaceQuai implements Singleton 
{
    private static EspaceQuai instance;
    private int nbVoiesDispo;
    private final List<Train> trainArret;
    
    private EspaceQuai(int nbVoies)
    {
        nbVoiesDispo = nbVoies;
        trainArret = new ArrayList<>();
    }

    public static EspaceQuai getInstance(int nbVoies) 
    {
        if (instance == null) 
        {
            instance = new EspaceQuai(nbVoies);
        }
        return instance;
    }

    /**
     * Pour marquer qu'une voie vient d'être occupée
     */
    public synchronized void occuperVoie(Train train) throws InterruptedException 
    {
        Objects.requireNonNull(train, "train cannot be null");

        final String threadName = Thread.currentThread().getName();
        String message = "";

        /*
         * Quand une voie est libérée, EspaceQuai notifie
         * tous les threads bloqués. Ainsi à son reveil,
         * le thread courant pourrait être devancé par un autre Train.
         * Conclusion : utiliser while pour retester la condition
         */
        while (nbVoiesDispo == 0) {
            train.setState(TrainState.EN_ATTENTE_VOIE_LIBRE);
            message = "Train <" + threadName + "> vient d'intégrer la file d'attente";
            System.out.println(message);
            wait();
        }

        nbVoiesDispo--;
        trainArret.add(train);
        train.setState(TrainState.EN_GARE);
        message = "Train <" + threadName + "> vient d'occuper un voie (nbVoiesDispo = " + nbVoiesDispo + " ) et a " + train.getNbPlaces() + " places.";
        System.out.println(message);
    }

    /**
     * Pour marquer qu'une voie vient d'être libérée
     */
    public synchronized void libererVoie(Train train) {
        Objects.requireNonNull(train, "train cannot be null");

        final String threadName = Thread.currentThread().getName();
        System.out.println("Train <" + threadName + "> vient de partir avec " + train.getNbPlaces() + " places restantes." );

        nbVoiesDispo++;
        trainArret.remove(train);
        train.setState(TrainState.PARTI);

        /*
         * Les threads Train et Voyageur peuvent tous être bloqué
         * et on veut être sûr de réveiller un Train
         * Conclusion : utiliser notifyAll()
         */
        notifyAll();
    }
    
    public synchronized void chercherTrain(Voyageur voyageur) throws InterruptedException
    {
        final String threadName = Thread.currentThread().getName();

	    	for(Train train : trainArret) 
	    	{
	    		if(train.getNbPlaces() > 0) 
	    		{
	    			train.decrement(); // méthode Synchronized pour la lecture et l'écriture, aucun soucis en vue.
	    			voyageur.setState(VoyageurState.MONTE_DANS_UN_TRAIN);
	    			System.out.println("Voyageur <" + threadName + "> vient de monter dans le train <" + train.getName() +">");
	    			break;
	    		}
	    	}
    }
}
