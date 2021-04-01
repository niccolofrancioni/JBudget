package it.unicam.cs.pa.jbudget105325;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * <p>Classe usata per salvare il ledger su un file JSON</p>
 *
 * @param <T>
 */
public class JsonPersistenceManager<T extends Account> implements PersistenceManager {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * <p>Richiama i vari metodi per salvare su file le singole liste del ledger</p>
     *
     * @param ledger
     */
    @Override
    public void save(Ledger ledger) {
        this.saveAccounts(ledger);
        this.saveTransactions(ledger);
        this.saveTags(ledger);
        this.saveScheduledTransaction(ledger);
    }

    /**
     * <p>Metodo usato per istanziare nuovamente la lista degli account con gli stessi valori
     * in quanto la classe RuntimeTypeAdapterFactory durante la deserializzazione elimina
     * il campo "type"</p>
     *
     * @param ledger
     * @return ArrayList<T>
     */
    private ArrayList<T> recreatingAccount(Ledger ledger) {
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i < ledger.getAccounts().size(); i++) {
            if (ledger.getAccounts().get(i) instanceof BankAccount == true) {
                BankAccount account = new BankAccount((BankAccount) ledger.getAccounts().get(i));
                list.add((T) account);
            }
            if (ledger.getAccounts().get(i) instanceof CreditCard == true && !(ledger.getAccounts().get(i) instanceof Bancomat)) {
                CreditCard account = new CreditCard((CreditCard) ledger.getAccounts().get(i));
                list.add((T) account);
            }
            if (ledger.getAccounts().get(i) instanceof Bancomat == true) {
                Bancomat account = new Bancomat((Bancomat) ledger.getAccounts().get(i));
                list.add((T) account);
            }
        }
        return list;
    }

    /**
     * <p>Salva su file la lista degli account</p>
     *
     * @param ledger
     */
    private void saveAccounts(Ledger ledger) {
        try (FileWriter writer = new FileWriter("account.json")) {
            ArrayList<T> list = this.recreatingAccount(ledger);
            Type listOfAccount = new TypeToken<ArrayList<Account>>() {
            }.getType();
            gson.toJson(list, listOfAccount, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Salva su file la lista delle transazioni</p>
     *
     * @param ledger
     */
    private void saveTransactions(Ledger ledger) {
        try (FileWriter writer = new FileWriter("transactions.json")) {
            gson.toJson(ledger.getTransactions(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Salva su file la lista dei tag</p>
     *
     * @param ledger
     */
    private void saveTags(Ledger ledger) {
        try (FileWriter writer = new FileWriter("tag.json")) {
            gson.toJson(ledger.getTags(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Salva su file la lista delle transazioni schedulate</p>
     *
     * @param ledger
     */
    private void saveScheduledTransaction(Ledger ledger) {
        try (FileWriter writer = new FileWriter("scheduled.json")) {
            gson.toJson(ledger.getScheduledTransactions(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Richiama i vari metodi per il caricamento da file delle liste del ledger</p>
     *
     * @return Ledger
     */
    @Override
    public Ledger load() {
        Ledger ledger = new Ledger(this.loadAccounts(), this.loadTransactions(), this.loadTag(), this.loadScheduledTransactions());
        return ledger;
    }

    /**
     * <p>Carica da file la lista degli account</p>
     *
     * @return ArrayList<T>
     */
    private ArrayList<T> loadAccounts() {
        try (Reader reader = new FileReader("account.json")) {
            if (this.checkForEmptyFile("account.json") == false) {
                Type listOfAccount = new TypeToken<ArrayList<Account>>() {
                }.getType();

                RuntimeTypeAdapterFactory<Account> adapter = com.google.gson.typeadapters.RuntimeTypeAdapterFactory.of(Account.class)
                        .registerSubtype(BankAccount.class, "conto")
                        .registerSubtype(CreditCard.class, "credito")
                        .registerSubtype(Bancomat.class, "bancomat");

                Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create();

                ArrayList<T> outList = gson.fromJson(reader, listOfAccount);

                return outList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<T> accounts = new ArrayList<>();
        return accounts;
    }

    /**
     * <p>Carica da file la lista delle transazioni</p>
     *
     * @return ArrayList<Transaction>
     */
    private ArrayList<Transaction> loadTransactions() {
        try (Reader reader = new FileReader("transactions.json")) {
            if (this.checkForEmptyFile("transactions.json") == false) {
                ArrayList<Transaction> transactions = new ArrayList<>();
                Type type = new TypeToken<ArrayList<Transaction>>() {
                }.getType();
                transactions = gson.fromJson(reader, type);
                return transactions;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Transaction> emptyTransactions = new ArrayList<>();
        return emptyTransactions;

    }

    /**
     * <p>Carica da file la lista dei tag</p>
     *
     * @return ArrayList<Tag>
     */
    private ArrayList<Tag> loadTag() {
        try (Reader reader = new FileReader("tag.json")) {
            if (this.checkForEmptyFile("tag.json") == false) {
                ArrayList<Tag> tags = new ArrayList<>();
                Type type = new TypeToken<ArrayList<Tag>>() {
                }.getType();
                tags = gson.fromJson(reader, type);
                return tags;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Tag> emptyTags = new ArrayList<>();
        return emptyTags;

    }

    /**
     * <p>Carica da file la lista delle transazioni schedulate</p>
     *
     * @return ArrayList<Transaction>
     */
    private ArrayList<Transaction> loadScheduledTransactions() {
        try (Reader reader = new FileReader("scheduled.json")) {
            if (this.checkForEmptyFile("scheduled.json") == false) {
                ArrayList<Transaction> transactions = new ArrayList<>();
                Type type = new TypeToken<ArrayList<Transaction>>() {
                }.getType();
                transactions = gson.fromJson(reader, type);
                return transactions;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Transaction> emptyTransactions = new ArrayList<>();
        return emptyTransactions;

    }

    /**
     * <p>Controlla se il file passato sia vuoto</p>
     *
     * @param pathName
     * @return boolean
     */
    private boolean checkForEmptyFile(String pathName) {
        File file = new File(pathName);
        if (file.length() == 0) {
            return true;
        } else {
            return false;
        }
    }


}
