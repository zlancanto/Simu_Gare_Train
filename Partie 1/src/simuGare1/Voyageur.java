package simuGare1;

import java.util.Objects;

public class Voyageur extends Thread {
    // En millisecondes
    public static final int DELAI_ENTRE_RECHERCHES = 100;

    private VoyageurState state;
    private final EspaceQuai quai;
    private final EspaceVente espaceVente;

    public Voyageur() 
    {
        state = VoyageurState.EN_ROUTE_VERS_GARE;
        quai = EspaceQuai.getInstance(0);
        espaceVente = EspaceVente.getInstance(0);
    }

    @Override
    public void run() 
    {
    	try {
			espaceVente.vendreBillet(this);
            while(this.state == VoyageurState.MUNI_D_UN_TICKET)
			{
                quai.chercherTrain(this); // on met l'attente dans le run plutôt que le moniteur.
                Thread.sleep(Voyageur.DELAI_ENTRE_RECHERCHES);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    /**
     * Permet de modifier l'état du voyageur
     * @param state nouvel état du voyageur
     */
    public void setState(VoyageurState state) {
        Objects.requireNonNull(state, "state cannot be null");
        this.state = state;
    }
}
