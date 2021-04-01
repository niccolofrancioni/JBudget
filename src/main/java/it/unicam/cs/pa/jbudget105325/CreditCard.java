package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * <p>Classe usata per implementare una carta di credito</p>
 */
public class CreditCard extends Account {

    protected final boolean BLOCKED = false;
    protected final boolean ACTIVATED = true;
    protected double creditLimit;
    protected boolean status;
    protected int id_account;


    public CreditCard(int id, String owner, double balance, String description, double limit, int id_account) {
        super(id, owner, balance, description, AccountType.LIABILITIES);
        this.id_account = id_account;
        this.creditLimit = limit;
        this.status = ACTIVATED;
        this.type = "credito";
    }

    public CreditCard(CreditCard account) {
        super(account.getID(), account.getOwner(), account.getBalance(), account.getDescription(), account.getAccountType());
        this.id_account = account.getId_account();
        this.creditLimit = account.getCreditLimit();
        this.status = account.isStatus();
        this.type = "credito";
        this.movements = account.getMovements();
    }

    public int getId_account() {
        return id_account;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public boolean isStatus() {
        return status;
    }

    private void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * <p>Effettua un movimento controllando che non si superi la soglia massima</p>
     *
     * @param movement
     */
    @Override
    public void addMovement(Movement movement) {
        if (this.balance >= this.creditLimit) {
            this.setStatus(BLOCKED);
        }
        if (this.status == BLOCKED) {
            throw new IllegalArgumentException("Operazione non consentita,carta bloccata");
        }
        if (this.status == ACTIVATED && this.balance + movement.getAmount() <= this.creditLimit && movement.getType()==MovementType.SPENDING) {
            this.balance = balance + movement.getAmount();
            this.movements.add(movement);
        }
        if (this.status == ACTIVATED && this.balance + movement.getAmount() <= this.creditLimit && movement.getType()==MovementType.INCOME) {
            this.balance = balance - movement.getAmount();
            this.movements.add(movement);
        }
        if (this.status == ACTIVATED && this.balance + movement.getAmount() > this.creditLimit && movement.getType()==MovementType.SPENDING) {
            throw new IllegalArgumentException("Operazione non consentita,si supererebbe la soglia massima");
        }

    }

    /**
     * <p>Il primo giorno del mese azzera il limite di spesa ,sottraendo le spese al conto corrente associato</p>
     *
     * @param ledger
     */
    public void resetSpending(Ledger ledger) throws Exception {
        if (LocalDate.now().getDayOfMonth() == 1) {
            Tag tag =new Tag(ledger.lastTagID(),"azzeramento carta","");
            ArrayList<Tag>tags= new ArrayList<>();
            tags.add(tag);
            Movement movement = new Movement(this.lastMovementId(), this.id_account, this.balance, MovementType.SPENDING, LocalDate.now(), tags, "Reset saldo carta di credito");
            ledger.getSingleAccount(this.id_account).addMovement(movement);
            this.balance = 0;
        }
    }
}
