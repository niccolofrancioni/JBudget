package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>Classe che implementa che contiene le liste di tutti
 * i conti ed i tag creati e di tutte le transazioni effettuate o schedulate.</p>
 *
 * @param <T>
 */
public class Ledger<T extends Account> implements LedgerInterface<T> {

    private ArrayList<T> accounts;
    private ArrayList<Transaction> transactions;
    private ArrayList<Tag> tags;
    private ArrayList<Transaction> scheduledTransactions;


    public Ledger(ArrayList<T> accounts, ArrayList<Transaction> transactions, ArrayList<Tag> tags, ArrayList<Transaction> scheduledTransactions) {
        this.accounts = accounts;
        this.transactions = transactions;
        this.tags = tags;
        this.scheduledTransactions = scheduledTransactions;
    }

    public Ledger() {
        this.accounts = new ArrayList<T>();
        this.transactions = new ArrayList<Transaction>();
        this.tags = new ArrayList<Tag>();
        this.scheduledTransactions = new ArrayList<Transaction>();

    }

    /**
     * <p>Restituisce il valore dell'ultimo ID usato per gli account</p>
     *
     * @return int
     */
    public int lastAccountID() {
        return this.accounts.size();
    }

    /**
     * <p>Restituisce il valore dell'ultimo ID usato per le transazioni</p>
     *
     * @return int
     */
    public int lastTransactionID() {
        return this.transactions.size();
    }

    /**
     * <p>Restituisce il valore dell'ultimo ID usato per i tag</p>
     *
     * @return int
     */
    public int lastTagID() {
        return this.tags.size();
    }

    /**
     * <p>Restituisce il valore dell'ultimo ID usato per le transazioni schedulate</p>
     *
     * @param int
     */
    public int lastScheduledTransactionID() {
        return this.scheduledTransactions.size();
    }

    /**
     * <p>Aggiunge un account alla lista</p>
     *
     * @param account
     */
    @Override
    public void addAccount(T account) {
        this.accounts.add(account);
    }

    /**
     * <p>Restituisce la lista degli account</p>
     *
     * @return ArrayList<T>
     */
    @Override
    public ArrayList<T> getAccounts() {
        return accounts;
    }

    /**
     * <p>Restituisce un account presente nella lista
     * nella posizione specificata</p>
     *
     * @param index
     * @return T
     */
    public T getAccount(int index) {
        return this.accounts.get(index);
    }

    /**
     * <p>Restituisce un account presente nella lista
     * che ha l'ID come quello passato</p>
     *
     * @param i
     * @return T
     * @throws Exception
     */
    @Override
    public T getSingleAccount(int i) throws Exception {
        for (int j = 0; j < accounts.size(); j++) {
            if (accounts.get(j).getID() == i) {
                return this.accounts.get(j);
            }
        }
        throw new Exception("Account non presente");
    }

    /**
     * <p>In base al controllo sulla data inserisce la transazione nella lista
     * di quelle future o eseguite.Se la transazione non è schedulata si occupa
     * di richiamare il metodo per eseguire i singoli movimenti che la compongono</p>
     *
     * @param transaction
     */
    @Override
    public void addTransaction(Transaction transaction) throws Exception {
        if (transaction.getDate().isAfter(LocalDate.now())) {
            this.scheduledTransactions.add(transaction);
        } else {
            transaction.executeTransaction(this);
            this.transactions.add(transaction);
        }
    }

    /**
     * <p>Genera la transazione con l'array di movimenti passato
     * e richiama il metodo per il suo inserimento</p>
     *
     * @param movements
     */
    @Override
    public void createTransaction(ArrayList<Movement> movements) throws Exception {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        for (int i = 0; i < movements.size(); i++) {
            tags.addAll(movements.get(i).getTag());
        }
        int j = 0;
        Transaction transaction = new Transaction(this.lastTransactionID(), movements, tags, movements.get(j).getDate());

        this.addTransaction(transaction);

    }

    /**
     * <p>Ritorna la lista dele transazioni</p>
     *
     * @return ArrayList<Transaction>
     */
    @Override
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * <p>Ritorna la lista dele transazioni schedulati</p>
     *
     * @return ArrayList<Transaction>
     */
    @Override
    public ArrayList<Transaction> getScheduledTransactions() {
        return scheduledTransactions;
    }

    /**
     * <p>Restituisce le transazioni che soddisfano un determinato predicato</p>
     *
     * @param predicate
     * @return ArrayList<Transaction>
     */
    @Override
    public ArrayList<Transaction> getTransactions(Predicate<Transaction> predicate) {
        ArrayList<Transaction> list = new ArrayList<Transaction>();
        list.addAll(this.transactions.stream()
                .filter(predicate)
                .collect(Collectors.<Transaction>toList()));

        return list;
    }

    /**
     * <p>Restituisce le transazioni schedulate che soddisfano un determinato predicato</p>
     *
     * @param predicate
     * @return ArrayList<Transaction>
     */
    @Override
    public ArrayList<Transaction> getScheduledTransactions(Predicate<Transaction> predicate) {
        ArrayList<Transaction> list = new ArrayList<Transaction>();
        list.addAll(this.scheduledTransactions.stream()
                .filter(predicate)
                .collect(Collectors.<Transaction>toList()));

        return list;
    }

    /**
     * <p>Ritorna una transazione presente nella lista
     * nella posizione specificata</p>
     *
     * @param index
     * @return Transaction
     */
    @Override
    public Transaction getSingleTransaction(int index) {
        return this.transactions.get(index);
    }

    /**
     * <p>Ritorna una transazione schedulata presente nella lista
     * nella posizione specificata</p>
     *
     * @param index
     * @return Transaction
     */
    public Transaction getSingleScheduledTransaction(int index) {
        return this.scheduledTransactions.get(index);
    }

    /**
     * <p>Restituisce la lista dei tag</p>
     *
     * @return ArrayList<Tag>
     */
    @Override
    public ArrayList<Tag> getTags() {
        return tags;
    }

    /**
     * <p>Ritorna un tag presente nella lista nella posizione specificata</p>
     *
     * @param index
     * @return Tag
     */
    public Tag getSingleTag(int index) {
        return this.tags.get(index);
    }

    /**
     * <p>Aggiunge un tag alla lista</p>
     *
     * @param name
     * @param description
     */
    @Override
    public void addTag(String name, String description) {
        if (name.equals("") || description.equals("")) {
            throw new IllegalArgumentException("Non sono ammessi campi vuoti");
        }
        if (this.checkForTagDuplicates(name) == false) {
            Tag tag = new Tag(this.lastTagID(), name, description);
            this.tags.add(tag);
        } else {
            throw new IllegalArgumentException("Tag presente");
        }
    }

    /**
     * <p>Controlla se un tag è già presente</p>
     *
     * @param name
     * @return
     */
    private boolean checkForTagDuplicates(String name) {
        boolean answer = false;
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getName().equals(name)) {
                answer = true;
            }
        }
        return answer;
    }

    /**
     * <p>Aggiunge un tag alla lista</p>
     *
     * @param tag
     */
    public void addTag(Tag tag) {
        if (tag.getName().equals("") || tag.getDescription().equals("")) {
            throw new IllegalArgumentException("Non sono ammessi campi vuoti");
        }
        if (this.checkForTagDuplicates(tag.getName()) == false) {
            this.tags.add(tag);
        } else {
            throw new IllegalArgumentException("Tag presente");
        }
    }

    @Override
    public void addScheduledTransaction(Transaction transaction) {
        this.scheduledTransactions.add(transaction);

    }

    /**
     * <p>Controlla ed esegue eventuali transazioni schedulate per la data odierna</p>
     *
     * @param predicate
     */
    @Override
    public void executingScheduledTransaction(Predicate<Transaction> predicate) throws Exception {
        ArrayList<Transaction> list = new ArrayList<Transaction>();
        list = this.getScheduledTransactions(predicate);
        this.scheduledTransactions.removeAll(list);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).executeTransaction(this);
        }
        this.transactions.addAll(list);


    }

    /**
     * <p>Controlla se per bancomat o carte di credito
     * bisogna azzerare le spese effettuate</p>
     * @throws Exception
     */
    public void resetLiabilities() throws Exception {
        for (int i=0;i<this.accounts.size();i++){
            if(accounts.get(i) instanceof CreditCard){
                 ((CreditCard) accounts.get(i)).resetSpending(this);
            }
            if(accounts.get(i) instanceof Bancomat){
                ((Bancomat) accounts.get(i)).resetSpending(this);
            }
        }
    }

    /**
     * <p>Rimuove un tag dalla lista</p>
     *
     * @param tag
     */
    @Override
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    /**
     * <p>Rimuove un tag dalla lista</p>
     *
     * @param id_tag
     */
    @Override
    public void removeTag(int id_tag) {
        this.tags.remove(this.tags.get(id_tag));
    }

    /**
     * <p>Rimuove un tag dalla lista</p>
     *
     * @param tagName
     */
    public void removeTag(String tagName) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getName().equals(tagName)) {
                this.tags.remove(i);
            }
        }

    }

    /**
     * <p>Rimuove un account dalla lista</p>
     *
     * @param id_account
     */
    @Override
    public void removeAccount(int id_account) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getID() == id_account) {
                this.accounts.remove(accounts.get(i));
            }
        }
    }

    /**
     * <p>Rimuove una transazione dalla lista</p>
     *
     * @param id_transaction
     */
    public void removeTransaction(int id_transaction) throws Exception {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getID() == id_transaction) {
               for(int j=0;j<transactions.get(i).movements().size();j++){
                  this.getSingleAccount(transactions.get(i).movements().get(i).getId()).removeMovement(transactions.get(i).movements().get(i).getId());
               }
                this.transactions.remove(transactions.get(i));
            }
        }
    }

    /**
     * <p>Rimuove una transazione schedulata dalla lista</p>
     *
     * @param id_transaction
     */
    public void removeScheduledTransaction(int id_transaction) {
        for (int i = 0; i < scheduledTransactions.size(); i++) {
            if (scheduledTransactions.get(i).getID() == id_transaction) {
                this.scheduledTransactions.remove(scheduledTransactions.get(i));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ledger)) return false;
        Ledger<?> ledger = (Ledger<?>) o;
        return Objects.equals(getAccounts(), ledger.getAccounts()) &&
                Objects.equals(getTransactions(), ledger.getTransactions()) &&
                Objects.equals(getTags(), ledger.getTags()) &&
                Objects.equals(getScheduledTransactions(), ledger.getScheduledTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccounts(), getTransactions(), getTags(), getScheduledTransactions());
    }

    @Override
    public String toString() {
        return "Ledger{" +
                "accounts=" + accounts +
                ", transactions=" + transactions +
                ", tags=" + tags +
                ", scheduledTransactions=" + scheduledTransactions +
                '}';
    }
}
