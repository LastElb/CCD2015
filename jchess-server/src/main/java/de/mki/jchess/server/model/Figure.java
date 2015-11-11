package de.mki.jchess.server.model;

import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */
public abstract class Figure<T extends Field> {
    String id;
    boolean isRemoved;
    T position;
    String name;
    String pictureId;

    public boolean isRemoved() {
        return isRemoved;
    }

    public Figure setRemoved(boolean removed) {
        isRemoved = removed;
        return this;
    }

    public T getPosition() {
        return position;
    }

    public Figure<T> setPosition(T position) {
        this.position = position;
        return this;
    }

    public String getName() {
        return name;
    }

    public Figure setName(String name) {
        this.name = name;
        return this;
    }

    public String getPictureId() {
        return pictureId;
    }

    public Figure setPictureId(String pictureId) {
        this.pictureId = pictureId;
        return this;
    }

    public List<Field> getPossibleMovements;

    public String getId() {
        return id;
    }

    public Figure setId(String id) {
        this.id = id;
        return this;
    }
}
