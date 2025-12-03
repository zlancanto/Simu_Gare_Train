package database;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import simuGare2.*;

/**
 *
 * In-memory database
 *
 * @author mihan
 * @author bot
 *
 */
public final class InMemoryDatabase implements Singleton 
{
    private static InMemoryDatabase instance;

    private int voyageurCount_;
    private int trainCount_;
    
    Map<Integer, Voyageur> voyageurs_;
    Map<Integer, Train> trains_;

    private InMemoryDatabase()
    {
        voyageurs_ = new HashMap<Integer, Voyageur>();
        trains_ = new HashMap<Integer, Train>();
    }

    public static InMemoryDatabase getInstance() 
    {
        if (instance == null) 
        {
            instance = new InMemoryDatabase();
        }
        return instance;
    }
    
    public synchronized Voyageur createVoyageur()
    {
        Voyageur v = new Voyageur();
        v.setIdentifiant(voyageurCount_);
        voyageurs_.put(voyageurCount_, v);
        voyageurCount_ ++;
        return v;
    }

    public synchronized Train createTrain(int nbPlaces)
    {
        Train t = new Train(nbPlaces);
        t.setIdentifiant(trainCount_);
        trains_.put(trainCount_, t);
        trainCount_ ++;
        return t;
    }
    
    public Collection<Voyageur> getVoyageurs()
    {
        return voyageurs_.values();
    }

    public Collection<Train> getTrains()
    {
        return trains_.values();
    }

}
