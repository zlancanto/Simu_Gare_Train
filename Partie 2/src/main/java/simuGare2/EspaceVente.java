package simuGare2;

/**
 * {@inheritDoc}
 */
public final class EspaceVente implements Singleton 
{
    private static final int TEMPS_IMPRESSION_BILLET = 500; // en ms
    private static EspaceVente instance;
    private int nbBillets;

    private EspaceVente(int nbBillets) 
    {
        this.nbBillets = nbBillets;
    }

    public static EspaceVente getInstance(int nbBillets) 
    {
        if (instance == null) 
        {
            instance = new EspaceVente(nbBillets);
        }
        return instance;
    }

    /**
     * Permet de vendre un billet
     * 'synchronized' car on accède à la variable d'état nbBillets
     * @param voyageur qui achète le billet
     * @throws InterruptedException en cas d'interruption
     * @throws NullPointerException si voyageur == null
     */
    public synchronized void vendreBillet(Voyageur voyageur) throws InterruptedException
    {
        final String threadName = Thread.currentThread().getName();
        String message = "";

        /**
         * Si un voyageur ne peut pas acheter de billet, il ne pourra jamais en acheter un.
         * Don il n'y a pas besoin de retester la condition.
         */
        if (nbBillets == 0) {
        	message = "Voyageur <" + threadName + "> ne peut pas acheter de billet";
            System.out.println(message);
        }
        else 
        {
	        Thread.sleep(TEMPS_IMPRESSION_BILLET);
	        nbBillets--;
	        voyageur.setState(VoyageurState.MUNI_D_UN_TICKET);
	        message = "Voyageur <" + threadName + "> vient d'acheter un billet (stock billets = " + nbBillets + " )";
	        System.out.println(message);
        }
    }
}
