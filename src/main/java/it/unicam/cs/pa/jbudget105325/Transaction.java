package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * <p>Classe usata per implementare una transazione</p>
 */
public class Transaction implements TransactionInterface {

    private int id;
    private ArrayList<Movement> movements;
    private ArrayList<Tag> tag;
    private LocalDate date;


    public Transaction(int id, ArrayList<Movement> movements, ArrayList<Tag> tag, LocalDate date) {
        this.id = id;
        this.movements = movements;
        this.tag = tag;
        this.date = date;
    }

    /**
     * <p>Esegue i movimenti che compongono la transazione</p>
     */
    @Override
    public void executeTransaction(Ledger ledger) throws Exception {
        for (int i = 0; i < movements.size(); i++) {
            int id = movements.get(i).getIdAccount();
            ledger.getSingleAccount(id).addMovement(movements.get(i));
        }
    }


    @Override
    public int getID() {
        return id;
    }

    @Override
    public ArrayList<Movement> movements() {
        return movements;
    }

    @Override
    public ArrayList<Tag> tags() {
        return tag;
    }

    @Override
    public void addTag(Tag tag) {
        this.tag.add(tag);
    }

    @Override
    public void removeTag(Tag tag) {
        this.tag.remove(tag);
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void addMovement(Movement movement) {
        this.movements.add(movement);
    }

    @Override
    public void removeMovement(Movement movement) {
        this.movements.remove(movement);
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }


}
