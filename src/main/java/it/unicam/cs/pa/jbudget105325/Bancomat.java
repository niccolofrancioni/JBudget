package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;

/**
 * <p>Classe usata per implementare un bancomat</p>
 */
public class Bancomat extends CreditCard {


    public Bancomat(int id, String owner, double balance, String description, double limit, int id_account) {
        super(id, owner, balance, description, limit, id_account);
        this.type = "bancomat";
    }

    public Bancomat(Bancomat account) {
        super(account.getID(), account.getOwner(), account.getBalance(), account.getDescription(), account.getCreditLimit(), account.getId_account());
        this.status = account.isStatus();
        this.type = "bancomat";
        this.movements = account.getMovements();
    }


    /**
     * <p>Il primo giorno del mese azzera il limite di spesa</p>
     */
    public void resetSpending() {
        if (LocalDate.now().getDayOfMonth() == 1) {
            this.balance = 0;
        }

    }
}
