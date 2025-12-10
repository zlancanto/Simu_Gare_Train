package simuGare2;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Train extends Thread {
    // En km/h
    private static final int VITESSE_MIN = 50;
    private static final int VITESSE_MAX = 300;
    // En seconde
    private static final int ARRET_TRAIN = 30;

   

    private int nbPlaces;
    private final int vitesse;
    private TrainState trainState;
    private final EspaceQuai quai;

    private int identifiant;
    public Train(int nbPlaces) {
        this.nbPlaces = nbPlaces;

        vitesse = ThreadLocalRandom
                .current()
                .nextInt(VITESSE_MIN, VITESSE_MAX + 1);

        trainState = TrainState.EN_ROUTE_VERS_GARE;
        quai = EspaceQuai.getInstance(0);
    }

    @Override
    public void run() {
        final long dureeTrajet = 10000/vitesse;

        try {
            // Le train se déplace vers la gare
            Thread.sleep(dureeTrajet);
            quai.occuperVoie(this);
            Thread.sleep(1000 * ARRET_TRAIN);
            quai.libererVoie(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setState(TrainState state) {
        Objects.requireNonNull(state, "state cannot be null");
        this.trainState = state;
    }
    
    public synchronized TrainState getTrainState() {
    	return this.trainState;
    }

    /**
     * Le train est un thread qui utilise la variable
     * partagée {@code quai}. Cependant il représente
     * un moniteur pour les Voyageurs. Ainsi il faut un
     * 'synchronized' sur l'accès à ces variables d'état. En
     * l'occurence 'nbPlaces'
     * @return le nombre de places dispo dans le train
     */
    public synchronized int getNbPlaces() 
    {
    	return this.nbPlaces;
    }

    /**
     * 'synchronized' car on modifie la variable d'état 'nbPlaces'
     */
    public synchronized void decrement() 
    {
    	this.nbPlaces --;
    }

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}
}
