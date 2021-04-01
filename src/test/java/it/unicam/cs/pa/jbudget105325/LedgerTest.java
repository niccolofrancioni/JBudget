package it.unicam.cs.pa.jbudget105325;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LedgerTest {



       Ledger ledger = new Ledger();
       BankAccount bankAccount;
       ArrayList<Tag> tags;
       ArrayList<Movement> listOfMovements;

       void setUpAccount(){
           String owner = "Mario Rossi";
           double balance = 1000;
           String description = "Conto corrente ";
           String iban = "IT00000000951E";
           String bank = "UBIBanca";
           bankAccount = new BankAccount(ledger.lastAccountID(),owner, balance, description, iban, bank);
           ledger.addAccount(bankAccount);
       }
       void setUpTags(){
           tags = new ArrayList<Tag>();
           Tag tag = new Tag(ledger.lastTagID(),"Istruzione", "Spese per scuola e università");
           Tag tag2 = new Tag(ledger.lastTagID(),"Commissioni", "Commissioni per operazioni bancarie");
           tags.add(tag);
       }

       void setUpMovements(){
           Movement movement=new Movement(bankAccount.lastMovementId(),bankAccount.getID(),256.50,MovementType.SPENDING, LocalDate.now(),tags,"Bonifico tasse universitarie");
           Movement movement2=new Movement(bankAccount.lastMovementId(),bankAccount.getID(),1, MovementType.SPENDING,LocalDate.now(),tags,"Commissioni bonifico");
           listOfMovements= new ArrayList<Movement>();
           listOfMovements.add(movement);
           listOfMovements.add(movement2);
       }



    /**
     * <p>Verifica che la transazione creata venga inserita nel ledger</p>
     */
   @Test
    void createTransactionTest() {
       try {
          setUpAccount();
          setUpTags();
          setUpMovements();
          ledger.createTransaction(listOfMovements);
          assertTrue(ledger.getTransactions().size()==1);
          assertTrue(ledger.getSingleAccount(bankAccount.getID()).getBalance()==742.50);
       }catch (Exception e){e.printStackTrace();}
    }

    /**
     * <p>Inserisce di proposito una transazione schedulata con una data già passata
     * per verificare il corretto funzionamento del metodo per il controllo delle
     * transazioni schedulate</p>
     */
    @Test
    void executingScheduledTransactionTest() {
      try {
          Transaction transaction = new Transaction(ledger.lastTransactionID(),listOfMovements, tags, LocalDate.parse("2020-05-01"));
          ledger.addScheduledTransaction(transaction);
         ledger.executingScheduledTransaction(TransactionPredicate.datePredicate(LocalDate.now()));
         assertTrue(ledger.getScheduledTransactions().isEmpty());
         assertTrue(ledger.getSingleAccount(bankAccount.getID()).getBalance()==485);
      }catch (Exception e){e.printStackTrace();}
    }
}