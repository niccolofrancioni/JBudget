package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * <p>Classe utilizzata per definire Predicate usati per filtrare le
 * transazioni</p>
 */
public class TransactionPredicate {
    /**
     * <p>Predicato usato per selezionare una transazione in base alla data</p>
     * @param date
     * @return Predicate<Transaction>
     */
    public static Predicate<Transaction> datePredicate(LocalDate date) {
        return p -> p.getDate().equals(date) || p.getDate().isBefore(date);
    }

    /**
     *p>Predicato usato per selezionare una transazione in base ad un tag</p>
     * @param tag
     * @return Predicate<Transaction>
     */
    public static Predicate<Transaction> budgetPredicate(Tag tag) {
        return p -> p.tags().contains(tag);
    }
}
