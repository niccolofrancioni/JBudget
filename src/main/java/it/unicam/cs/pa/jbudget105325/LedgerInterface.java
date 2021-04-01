package it.unicam.cs.pa.jbudget105325;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * <p>Interfaccia implementata dalle classi che hanno la responsabilità di
 * gestire tutti i dati dell’applicazione</p>
 * @param <T>
 */
public interface LedgerInterface<T extends Account> {

    public ArrayList<T> getAccounts();

    public T getSingleAccount(int i) throws Exception;

    public void addTransaction(Transaction transaction) throws Exception;

    public void createTransaction(ArrayList<Movement>movements) throws Exception;

    public ArrayList<Transaction> getTransactions();

    public ArrayList<Transaction> getTransactions(Predicate<Transaction> predicate);

    public ArrayList<Transaction> getScheduledTransactions();

    public ArrayList<Transaction> getScheduledTransactions(Predicate<Transaction> predicate);

    public Transaction getSingleTransaction(int index);

    public ArrayList<Tag> getTags();

    public void addAccount(T account);

    public void addTag(String name, String description);

    public void addScheduledTransaction(Transaction transaction);

    public void executingScheduledTransaction(Predicate<Transaction> predicate) throws Exception;

    public void removeTag(Tag tag);

    public void removeTag(int id_tag);

    public void removeAccount(int id_account);

}
