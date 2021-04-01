package it.unicam.cs.pa.jbudget105325;

/**
 * <p>Classe usata per implementare un conto corrente</p>
 */
public class BankAccount extends Account {

    private String iban;
    private String bank;

    public BankAccount(int id, String owner, double balance, String description, String iban, String bank) {
        super(id, owner, balance, description, AccountType.ASSET);
        this.iban = iban;
        this.bank = bank;
        this.type = "conto";

    }

    public BankAccount(BankAccount account) {
        super(account.getID(), account.getOwner(), account.getBalance(), account.getDescription(), account.getAccountType());
        this.iban = account.getIban();
        this.bank = account.getBank();
        this.type = "conto";
        this.movements = account.getMovements();
    }


    public String getIban() {
        return iban;
    }

    public String getBank() {
        return bank;
    }

}
