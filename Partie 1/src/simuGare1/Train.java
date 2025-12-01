package simuGare1;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Train extends Thread {
    // En km/h
    private static final int VITESSE_MIN = 50;
    private static final int VITESSE_MAX = 300;
    // En seconde
    private static final int ARRET_TRAIN = 5;

    private static final int CAPACITE_MIN = 0;
    private static final int CAPACITE_MAX = 100;

    private int nbPlaces;
    private final int vitesse;
    private TrainState state;
    private final EspaceQuai quai;

    public Train() {
        nbPlaces = ThreadLocalRandom
                .current()
                .nextInt(CAPACITE_MIN, CAPACITE_MAX + 1);

        vitesse = ThreadLocalRandom
                .current()
                .nextInt(VITESSE_MIN, VITESSE_MAX + 1);

        state = TrainState.EN_ROUTE_VERS_GARE;
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
        this.state = state;
    }
    
    public synchronized int getNbPlaces() 
    {
    	return this.nbPlaces;
    }
    
    public synchronized void decrement() 
    {
    	this.nbPlaces --;
    }
}
