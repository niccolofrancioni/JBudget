package it.unicam.cs.pa.jbudget105325;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {
    Report report = new Report();
    Report report2 = new Report();
    BankAccount bankAccount;
    Tag tag;
    Tag tag2;
    ArrayList<Tag>tags;
    ArrayList<Tag>tags2;
    Movement movement;
    Movement movement2;

    void setUpAccount(){
        String owner = "Mario Rossi";
        double balance = 1000;
        String description = "Conto corrente ";
        String iban = "IT00000000951E";
        String bank = "UBIBanca";
        bankAccount = new BankAccount(0,owner, balance, description, iban, bank);

    }
    void setUpTags(){
        tags = new ArrayList<>();
        tags2 = new ArrayList<>();
        tag = new Tag(0,"Alimentari", "Spese per generi alimentari");
        tag2 = new Tag(1,"Manutenzione auto", "Spese per la manutenzione auto");
        tags.add(tag);
        tags2.add(tag2);
    }

    void setUpMovements(){
         movement=new Movement(bankAccount.lastMovementId(),bankAccount.getID(),150,MovementType.SPENDING, LocalDate.now(),tags,"Spesa generi alimentari");
         movement2=new Movement(bankAccount.lastMovementId(),bankAccount.getID(),250, MovementType.SPENDING,LocalDate.now(),tags2,"Manutenzione auto");

    }

    /**
     * <p>Aggiunge due movimenti all'account e controlla che le spese
     * e le entrate dei report coincidano con l'importo dei movimenti</p>
     */
    @Test
    void generateReportTest(){
        setUpAccount();
        setUpTags();
        setUpMovements();
        bankAccount.addMovement(movement);
        bankAccount.addMovement(movement2);
        report.generateReport(bankAccount,tag);
        report2.generateReport(bankAccount,tag2);
        assertTrue(report.getIncomes()==0 && report.getSpending()==150);
        assertTrue(report2.getIncomes()==0 && report2.getSpending()==250);
    }
}