package de.mki.jchess.server.model;

import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 * @param <T> The implemented {@link Field} of your game mode
 */
public abstract class Figure<T extends Field> {
    String id;
    boolean isRemoved;
    T position;
    String name;
    String pictureId;
    Client client;

    public Figure(Client client) {
        isRemoved = false;
        this.client = client;
    }

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

    /**
     * Returns a list of possible {@link Field}s. This list may include {@link Field}s from {@link #getPossibleSpecialMovements(Chessboard)}
     * @param chessboard The instance of the {@link Chessboard} of the current {@link Game}
     * @return Returns a list of {@link Field}.
     */
    public abstract List<T> getPossibleMovements(Chessboard chessboard);

    /**
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a list of possible {@link Field}s for special movements like castling, en passant and pawn promotion.
     */
    public abstract List<T> getPossibleSpecialMovements(Chessboard chessboard);

    public String getId() {
        return id;
    }

    public Figure setId(String id) {
        this.id = id;
        return this;
    }
}
