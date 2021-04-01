package it.unicam.cs.pa.jbudget105325;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankAccountTest {

    BankAccount account;
    Movement movement;
    ArrayList<Tag> tags;


     void setUpAccount(){
      String owner = "Mario Rossi";
      double balance = 1000;
      String description = "Conto corrente ";
      String iban = "IT00000000951E";
      String bank = "UBIBanca";
     account=new BankAccount(0,owner,balance,description,iban,bank);
    }

    void setUpTag(){
        tags = new ArrayList<Tag>();
        Tag tag = new Tag(0,"Bancomat", "Prelievo o pagamento bancomat");
        tags.add(tag);
    }


    void setUpMovement(){
        movement=new Movement(account.lastMovementId(),account.getID(),100,MovementType.SPENDING, LocalDate.now(),tags,"Prelievo bancomat");
    }

    /**
     * <p>Controlla che il movimento venga aggiunto
     * e che il saldo venga decrementato</p>
     */
    @Test
    void addMovementTest() {
        setUpAccount();
        setUpTag();
        setUpMovement();
        ArrayList<Movement> arrayExpected = new ArrayList<>();
        arrayExpected.add(movement);
        account.addMovement(movement);
        assertEquals(arrayExpected,account.getMovements());
        assertTrue(account.getBalance()==900);
    }
}