package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * <p>Classe usata per implementare un movimento</p>
 *
 * @param <T>
 */
public class Movement<T extends AccountInterface> implements MovementInterface<T> {

    private int id;
    private int id_account;
    private double amount;
    private MovementType type;
    private LocalDate date;
    private ArrayList<Tag> tag;
    private String description;


    public Movement(int id, int id_account, double amount, MovementType type, LocalDate date, ArrayList<Tag> tag, String description) {
        this.id = id;
        this.id_account = id_account;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.tag = tag;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public MovementType getType() {
        return type;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public int getIdAccount() {
        return id_account;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public ArrayList<Tag> getTag() {
        return tag;
    }

    @Override
    public void addTag(Tag tag) {
        this.tag.add(tag);
    }

    @Override
    public void removeTag(Tag tag) {
        this.tag.remove(tag);
    }

    @Override
    public void setIdAccount(int id_account) {
        this.id_account = id_account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movement)) return false;
        Movement<?> movement = (Movement<?>) o;
        return id_account == movement.id_account &&
                Double.compare(movement.getAmount(), getAmount()) == 0 &&
                getType() == movement.getType() &&
                getDate().equals(movement.getDate()) &&
                getTag().equals(movement.getTag()) &&
                getDescription().equals(movement.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_account, getAmount(), getType(), getDate(), getTag(), getDescription());
    }

    @Override
    public String toString() {
        return "Movement{" +
                "id=" + id +
                ", id_account=" + id_account +
                ", amount=" + amount +
                ", type=" + type +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
