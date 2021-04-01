package it.unicam.cs.pa.jbudget105325;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BancomatTest {


    Bancomat bancomat;
    ArrayList<Tag> tags;
    Movement movement;
    Movement movement2;


    void setUpAccount(){
        String owner = "Mario Rossi";
        double balance = 1000;
        String description = "Carta di credito ";
        bancomat= new Bancomat(1,owner,0,description,500,0);
    }
    void setUpTag(){
        tags = new ArrayList<Tag>();
        Tag tag = new Tag(1,"Manutenzione auto", "Spese per la manutenzione dell'auto");
        tags.add(tag);
    }

    void setUpMovements(){
        movement=new Movement(bancomat.lastMovementId(),bancomat.getID(),100,MovementType.SPENDING, LocalDate.now(),tags,"Pagamento presso officina");
        movement2=new Movement(bancomat.lastMovementId(),bancomat.getID(),450,MovementType.SPENDING, LocalDate.now(),tags,"Pagamento presso officina");
    }

    /**
     * <p>Controlla che il movimento venga aggiunto
     * che il saldo venga decrementato e che venga
     * lanciata un'eccezione se l'operazione non è consentita</p>
     */
    @Test
    void addMovementTest() {
        ArrayList<Movement> arrayExpected = new ArrayList<>();
        setUpAccount();
        setUpTag();
        setUpMovements();
        arrayExpected.add(movement);
        bancomat.addMovement(movement);
        assertEquals(arrayExpected,bancomat.getMovements());
        assertTrue(bancomat.getBalance()==100);
        assertThrows(IllegalArgumentException.class , ()-> bancomat.addMovement(movement2),"Operazione non consentita");
    }
}