package de.mki.jchess.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Optional;

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
    @JsonIgnore
    Client client;
    Optional<T> hypotheticalPosition;
    Optional<Boolean> hypotheticalRemoved;

    public Figure(Client client) {
        isRemoved = false;
        this.client = client;
        hypotheticalPosition = Optional.empty();
        hypotheticalRemoved = Optional.empty();
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
     * Returns a list of possible {@link Field}s the figure can move to. This list may include {@link Field}s from {@link #getPossibleSpecialMovements(Chessboard)}
     * The List will overlap with {@link #getAttackableFields()}, but due to some chess rules not all must be included.
     * @param chessboard The instance of the {@link Chessboard} of the current {@link Game}
     * @return Returns a list of {@link Field}.
     */
    public abstract List<T> getPossibleMovements(Chessboard chessboard);

    /**
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a list of possible {@link Field}s for special movements like castling, en passant and pawn promotion.
     */
    public abstract List<T> getPossibleSpecialMovements(Chessboard chessboard);

    /**
     * @return This method returns a list of all fields the figure could attack.
     */
    public abstract List<T> getAttackableFields();

    public String getId() {
        return id;
    }

    public Figure setId(String id) {
        this.id = id;
        return this;
    }

    public Client getClient() {
        return client;
    }

    /**
     * Returns the hypothetical position or the actual position, if there is no hypothetical one set.
     * @return Returns a {@link T Field}
     */
    public T getHypotheticalPosition() {
        return hypotheticalPosition.orElse(getPosition());
    }

    /**
     * Set a hypothetical position or use {@link null} to reset the field.
     * @param position The hypothetical position or null
     */
    public void setHypotheticalPosition(T position) {
        hypotheticalPosition = Optional.ofNullable(position);
    }

    /**
     * Returns the hypothetical position or the actual position, if there is no hypothetical one set.
     * @return Returns a {@link T Field}
     */
    public boolean getHypotheticalRemoved() {
        return hypotheticalRemoved.orElse(isRemoved);
    }

    /**
     * Set if the figure is hypothetically removed or use {@link null} to reset the field.
     * @param isRemoved If the figure is hypothetically removed or null
     */
    public void setHypotheticalRemoved(Boolean isRemoved) {
        hypotheticalRemoved = Optional.ofNullable(isRemoved);
    }
}
