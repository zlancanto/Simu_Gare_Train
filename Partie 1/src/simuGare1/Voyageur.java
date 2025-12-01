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
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
		if(this.state == VoyageurState.MUNI_D_UN_TICKET) 
		{
			// Il cherche un train avec une place libre.
		}
		
    }
    
    
    public void setState(VoyageurState state) {
        Objects.requireNonNull(state, "state cannot be null");
        this.state = state;
    }
    
    public synchronized VoyageurState getVoyageurState() {
    	return this.state;
    }
}
