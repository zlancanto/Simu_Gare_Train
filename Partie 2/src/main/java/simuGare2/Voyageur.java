package simuGare2;

import java.util.Objects;

public class Voyageur extends Thread {
    // En millisecondes
    public static final int DELAI_ENTRE_RECHERCHES = 1000; // en ms. Ici = 1s

    private VoyageurState state;
    private final EspaceQuai quai;
    private final EspaceVente espaceVente;

    private int identifiant;
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
     * @param state le nouvel état du voyageur
     */
    public void setState(VoyageurState state) {
        Objects.requireNonNull(state, "state cannot be null");
        this.state = state;
    }

    /**
     * Permet de récupérer l'état du voyageur
     */
    public synchronized VoyageurState getVoyageurState() {
    	return this.state;
    }

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int numero) {
		this.identifiant = numero;
	}
}
