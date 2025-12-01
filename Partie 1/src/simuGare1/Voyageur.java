package simuGare1;

public class Voyageur extends Thread {
    // En millisecondes
    private static final int DELAI_ENTRE_RECHERCHES = 100;

    private VoyageurState state;
    private final EspaceQuai quai;

    public Voyageur() {
        state = VoyageurState.EN_ROUTE_VERS_GARE;
        quai = EspaceQuai.getInstance(0);
    }
}
