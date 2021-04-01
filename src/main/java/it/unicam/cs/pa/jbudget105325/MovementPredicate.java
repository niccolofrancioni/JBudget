package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * <p>Classe utilizzata per definire Predicate usati per filtrare i movimenti all’interno di una lista</p>
 */
public class MovementPredicate {

    /**
     * <p>Predicato usato per selezionare un movimento in base alla data</p>
     *
     * @param date
     * @return Predicate<Movement>
     */
    public static Predicate<Movement> datePredicate(LocalDate date) {
        return p -> p.getDate().equals(date);
    }

    /**
     * <p>Predicato usato per selezionare un movimento in base al tag</p>
     *
     * @param tag
     * @return Predicate<Movement>
     */
    public static Predicate<Movement> budgetPredicate(Tag tag) {
        return p -> p.getTag().contains(tag);
    }

}
