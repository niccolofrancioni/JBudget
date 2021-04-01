package it.unicam.cs.pa.jbudget105325;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>Classe usata per implementare un account</p>
 */
public class Account implements AccountInterface {

    protected int id;
    protected String owner;
    protected double balance;
    protected String description;
    protected ArrayList<Movement> movements;
    protected AccountType accountType;
    protected String type;


    public Account(int id, String owner, double balance, String description, AccountType accountType) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
        this.description = description;
        this.accountType = accountType;
        this.movements = new ArrayList<Movement>();
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public ArrayList<Movement> getMovements() {
        return movements;
    }

    /**
     * <p>Restituisce una lista di movimenti in base a un predicato</p>
     *
     * @param predicate
     * @return ArrayList<Movement>
     */
    @Override
    public ArrayList<Movement> getMovements(Predicate<Movement> predicate) {
        ArrayList<Movement> list = new ArrayList<Movement>();
        list.addAll(this.movements.stream()
                .filter(predicate)
                .collect(Collectors.<Movement>toList()));
        return list;
    }

    /**
     * <p>Aggiunge un movimento alla lista ed effettua l'incremento/decremento del bilancio</p>
     *
     * @param movement
     */
    @Override
    public void addMovement(Movement movement) {
        if (movement.getType() == MovementType.INCOME) {
            this.balance = balance + movement.getAmount();

        } else {
            if (this.balance - movement.getAmount() >= 0) {
                this.balance = balance - movement.getAmount();
            } else {
                throw new IllegalArgumentException("Operazione non consentita");
            }
        }
        this.movements.add(movement);

    }

    /**
     * <p>Rimuove un movimento dalla lista</p>
     *
     * @param index
     */
    @Override
    public void removeMovement(int index) {
        for (int i = 0; i < movements.size(); i++) {
            if (movements.get(i).getId() == index) {
                if(movements.get(i).getType()==MovementType.SPENDING){this.balance=balance+movements.get(i).getAmount();}
                if(movements.get(i).getType()==MovementType.INCOME){this.balance=balance-movements.get(i).getAmount();}
                this.movements.remove(i);
            }
        }
    }

    /**
     * Ritorna l'ultimo id inserito
     *
     * @return
     */
    @Override
    public int lastMovementId() {
        return this.movements.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return id == account.id &&
                Double.compare(account.getBalance(), getBalance()) == 0 &&
                Objects.equals(getOwner(), account.getOwner()) &&
                Objects.equals(getDescription(), account.getDescription()) &&
                Objects.equals(getMovements(), account.getMovements()) &&
                getAccountType() == account.getAccountType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getOwner(), getBalance(), getDescription(), getMovements(), getAccountType());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", balance=" + balance +
                ", description='" + description + '\'' +
                ", movements=" + movements +
                ", accountType=" + accountType +
                ", type='" + type + '\'' +
                '}';
    }
}
