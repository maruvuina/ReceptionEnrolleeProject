package by.epam.receptionenrollee.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable, Cloneable {
    private int id;

    public Entity() {}

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
