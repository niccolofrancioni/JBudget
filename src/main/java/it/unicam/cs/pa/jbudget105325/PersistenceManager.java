package it.unicam.cs.pa.jbudget105325;

/**
 * <p>Interfaccia implementata dalle classi che si occupano della persistenza dei dati</p>
 */
public interface PersistenceManager {


    void save(Ledger ledger);

    Ledger load();
}
