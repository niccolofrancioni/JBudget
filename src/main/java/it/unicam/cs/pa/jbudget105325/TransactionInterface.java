package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * <p>Interfaccia implementata dalle classi che rappresentato una
 * transazione</p>
 */
public interface TransactionInterface {

    public void executeTransaction(Ledger ledger) throws Exception;

    public int getID();

    public ArrayList<Movement> movements();

    public ArrayList<Tag> tags();

    public void addTag(Tag tag);

    public  void  removeTag(Tag tag);

    public LocalDate getDate();

    public void addMovement(Movement movement);

    public void removeMovement(Movement movement);

    public void setDate(LocalDate date);


}
