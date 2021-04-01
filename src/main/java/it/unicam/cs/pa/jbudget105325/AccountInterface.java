package it.unicam.cs.pa.jbudget105325;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * <p>Interfaccia implementata dalle classi che rappresentano un conto</p>
 */
public interface AccountInterface {

    public int getID();

    public String getOwner();

    public String getDescription();

    public double getBalance();

    public AccountType getAccountType();

    public ArrayList<Movement> getMovements();

    public ArrayList<Movement> getMovements(Predicate<Movement> predicate);

    public int lastMovementId();

    public void addMovement(Movement movement);

    public void removeMovement(int index);
}
