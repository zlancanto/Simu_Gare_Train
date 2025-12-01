package simuGare1;

/**
 * {@inheritDoc}
 */
public final class EspaceVente implements Singleton 
{
    private static final int TEMPS_IMPRESSION_BILLET = 50;
    private static EspaceVente instance;
    private int nbBillets;

    private EspaceVente(int nbBillets) 
    {
        this.nbBillets = nbBillets;
    }

    public static EspaceVente createInstance(int nbBillets) 
    {
        if (instance == null) 
        {
            instance = new EspaceVente(nbBillets);
        }
        return instance;
    }

    /**
     * Permet de vendre un billet
     */
    public synchronized boolean vendre() throws InterruptedException
    {
        final String threadName = Thread.currentThread().getName();
        String message = "";

        /*
         * Si un voyageur ne peut pas acheter de billet, il ne pourra jamais en acheter un.
         *
         */
        if (nbBillets == 0) {
            return false;
        }

        Thread.sleep(TEMPS_IMPRESSION_BILLET);
        nbBillets--;
        message = "Voyageur <" + threadName + "> vient d'acheter un billet (stock billets = " + nbBillets + " )";
        System.out.println(message);
        return true;
    }
}
