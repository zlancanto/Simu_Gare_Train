package simuGare1;

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
     * synchronized' sur la methode car on agit sur nos
     * variables d'état 'nbVoiesDispo' & 'trainArret'
     * @param train qui doit occuper la voie
     * @throws InterruptedException lors de l'exécution
     * @throws NullPointerException si train == null
     */
    public synchronized void occuperVoie(Train train) throws InterruptedException 
    {
        Objects.requireNonNull(train, "train cannot be null");

        final String threadName = Thread.currentThread().getName();
        String message;

        /*
         * Dans notre système les Voyageurs ne sont jamais bloqués
         * Quand une voie est libérée, EspaceQuai notifie
         * un seul Train bloqué. Ainsi à son reveil,
         * le Train courant ne peut être devancé par aucun autre Train.
         * Conclusion : utiliser if est plus optimal
         */
        if (nbVoiesDispo == 0) {
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
     * synchronized' sur la methode car on agit sur nos
     * variables d'état 'nbVoiesDispo' & 'trainArret'
     * @param train qui libère la voie
     * @throws NullPointerException si train == null
     */
    public synchronized void libererVoie(Train train)
    {
        Objects.requireNonNull(train, "train cannot be null");

        nbVoiesDispo++;
        trainArret.remove(train);
        train.setState(TrainState.PARTI);

        final String threadName = Thread.currentThread().getName();
        System.out.println("Train <" + threadName + "> vient de partir avec " + train.getNbPlaces() + " places restantes." );

        /*
         * Il n'y a que des Threads train qui dorment.
         * Pas besoin de tous les réveiller, un seul suffit
         */
        notify();
    }

    /**
     * Permet aux Voyageurs de chercher un train
     * 'synchronized' car après avoir vérifié que
     * "train.getNbPlaces() > 0", il se pourrait
     * qu'un autre Voyageur décrémente le nombre
     * de places du train avant le Voyageur courant. Amenant
     * ainsi la condition "train.getNbPlaces() > 0" à false
     * @param voyageur qui cherche le train
     * @throws InterruptedException en cas d'interruption
     */
    public synchronized void chercherTrain(Voyageur voyageur) throws InterruptedException
    {
        Objects.requireNonNull(voyageur, "voyageur cannot be null");
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
