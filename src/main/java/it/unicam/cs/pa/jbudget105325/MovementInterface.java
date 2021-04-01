package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * <p>Interfaccia implementata dalle classi che rappresentano un singolo movimento</p>
 * @param <T>
 */
public interface MovementInterface<T extends  AccountInterface> {
    public String getDescription();

    public MovementType getType();

    public double getAmount();

    public int getIdAccount();

    public LocalDate getDate();

    public ArrayList<Tag> getTag();

    public void addTag(Tag tag);

    public void removeTag(Tag tag);

    public void setIdAccount(int id_account);
}
