package it.unicam.cs.pa.jbudget105325;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * <p>Controller dell'applicazione</p>
 *
 * @param <T>
 */
public class Controller<T extends Account> {

    Ledger ledger;

    public Controller(Ledger ledger) {
        this.ledger = ledger;
    }


    public Ledger getLedger() {
        return ledger;
    }

    /**
     * <p>Restituisce la lista delgi account presente nel ledger</p>
     *
     * @return ArrayList<T>
     */
    public ArrayList<T> getAccounts() {
        return this.ledger.getAccounts();
    }

    /**
     * <p>Restituisce un account presente nella lista del ledger,
     * che si trova nella posizione specificata</p>
     *
     * @param index
     * @return AccountInterface
     */
    public AccountInterface getAccount(int index) {
        return this.ledger.getAccount(index);
    }

    /**
     * <p>Restituisce un account presente nella lista del ledger,
     * che ha l'ID come quello passato</p>
     *
     * @param i
     * @return AccountInterface
     * @throws Exception
     */
    public AccountInterface getSingleAccount(int i) throws Exception {
        return this.ledger.getSingleAccount(i);
    }

    /**
     * <p>Crea una transazione che verrà aggiunta nella lista del ledger</p>
     *
     * @param movements
     * @throws Exception
     */
    public void createTransaction(ArrayList<Movement> movements) throws Exception {
        this.ledger.createTransaction(movements);
    }

    /**
     * <p>Restituisce la lista delle transazioni presente del ledger</p>
     *
     * @return ArrayList<Transaction>
     */
    public ArrayList<Transaction> getTransactions() {
        return this.ledger.getTransactions();
    }

    public ArrayList<Transaction> getTransactions(Predicate<Transaction> predicate) {
        return this.ledger.getTransactions(predicate);
    }

    /**
     * <p>Restituisce la lista delle transazioni schedulate presente del ledger</p>
     *
     * @return ArrayList<Transaction>
     */
    public ArrayList<Transaction> getScheduledTransactions() {
        return this.ledger.getScheduledTransactions();
    }

    public ArrayList<Transaction> getScheduledTransactions(Predicate<Transaction> predicate) {
        return this.ledger.getScheduledTransactions(predicate);
    }

    /**
     * <p>Restituisce una transazione della lista del ledger,
     * che si trova nella posizione specificata</p>
     *
     * @param index
     * @return Transaction
     */
    public Transaction getSingleTransaction(int index) {
        return this.ledger.getSingleTransaction(index);
    }

    /**
     * <p>Restituisce una transazione schedulata della lista del ledger,
     * che si trova nella posizione specificata</p>
     *
     * @param index
     * @return Transaction
     */
    public Transaction getSingleScheduledTransaction(int index) {
        return this.ledger.getSingleScheduledTransaction(index);
    }

    /**
     * <p>Restituisce la lista dei tag presente nel ledger</p>
     *
     * @return ArrayList<Tag>
     */
    public ArrayList<Tag> getTags() {
        return this.ledger.getTags();
    }

    /**
     * <p>Restituisce un tag della lista del ledger,
     * che si trova nella posizione specificata</p>
     *
     * @param index
     * @return Tag
     */
    public Tag getSingleTag(int index) {
        return this.ledger.getSingleTag(index);
    }

    /**
     * <p>Aggiunge un account alla lista presente nel ledger</p>
     *
     * @param account
     */
    public void addAccount(T account) {
        this.ledger.addAccount(account);
    }

    /**
     * <p>Aggiunge un tag alla lista presente nel ledger</p>
     *
     * @param name
     * @param description
     */
    public void addTag(String name, String description) {
        this.ledger.addTag(name, description);
    }

    /**
     * <p>Aggiunge un tag alla lista presente nel ledger</p>
     *
     * @param name
     * @param description
     */
    public void addTag(Tag tag) {
        this.ledger.addTag(tag);
    }

    /**
     * <p>Aggiunge una transazione schedulata alla lista presente nel ledger</p>
     *
     * @param transaction
     */
    public void addScheduledTransaction(Transaction transaction) {
        this.ledger.addScheduledTransaction(transaction);
    }

    /**
     * <p>Controlla ed esegue eventuali transazioni schedulate per la data odierna</p>
     *
     * @param predicate
     * @throws Exception
     */
    public void executingScheduledTransaction(Predicate<Transaction> predicate) throws Exception {
        this.ledger.executingScheduledTransaction(predicate);
    }

    /**
     * <p>Controlla se per bancomat o carte di credito
     * bisogna azzerare le spese effettuate</p>
     * @throws Exception
     */
    public void resetLiabilities() throws Exception {
        this.ledger.resetLiabilities();
    }

    /**
     * <p>Rimuove il tag passato dalla, lista presente nel ledger</p>
     *
     * @param tag
     */
    public void removeTag(Tag tag) {
        this.ledger.removeTag(tag);
    }

    /**
     * <p>Rimuove il tag con l'ID passato, dalla lista presente nel ledger</p>
     *
     * @param id_tag
     */
    public void removeTag(int id_tag) {
        this.ledger.removeTag(id_tag);
    }

    /**
     * <p>Rimuove il tag con il nome passato, dalla lista presente nel ledger</p>
     *
     * @param tagName
     */
    public void removeTag(String tagName) {
        this.ledger.removeTag(tagName);
    }

    /**
     * <p>Rimuove l'account con l'ID passato,dalla lista presente nel ledger</p>
     *
     * @param id_account
     */
    public void removeAccount(int id_account) {
        this.ledger.removeAccount(id_account);
    }

    /**
     * <p>Rimuove la transazione con l'ID passato,dalla lista presente nel ledger</p>
     *
     * @param id_transaction
     */
    public void removeTransaction(int id_transaction) throws Exception {
        this.ledger.removeTransaction(id_transaction);
    }

    /**
     * <p>Rimuove la transazione schedulata con l'ID passato,dalla lista presente nel ledger</p>
     *
     * @param id_transaction
     */
    public void removeScheduledTransaction(int id_transaction) {
        this.ledger.removeScheduledTransaction(id_transaction);
    }

    /**
     * <p>Restituisce l'ultimo ID usato per gli account</p>
     *
     * @return int
     */
    public int lastAccountID() {
        return this.ledger.lastAccountID();
    }

    /**
     * <p>Restituisce l'ultimo ID usato per le transazioni</p>
     *
     * @return int
     */
    public int lastTransactionID() {
        return this.ledger.lastTransactionID();
    }

    /**
     * <p>Restituisce l'ultimo ID usato per i tag</p>
     *
     * @return int
     */
    public int lastTagID() {
        return this.ledger.lastTagID();
    }
}
