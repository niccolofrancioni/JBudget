package it.unicam.cs.pa.jbudget105325;

import java.util.Objects;

/**
 * <p>Classe usata per definire una categoria di movimento</p>
 */
public class Tag implements TagInterface {


    private int id;
    private String name;
    private String description;


    public Tag(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return id == tag.id &&
                getName().equals(tag.getName()) &&
                getDescription().equals(tag.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
