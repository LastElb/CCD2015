package de.mki.jchess.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.commons.Client;
import de.mki.jchess.commons.Field;

import java.util.List;
import java.util.Optional;

/**
 * Created by Igor on 11.11.2015.
 * @param <T> The implemented {@link Field} of your game mode
 */
public abstract class Figure<T extends Field> extends de.mki.jchess.commons.Figure<T> {
    @JsonIgnore
    Optional<T> hypotheticalPosition;
    @JsonIgnore
    Optional<Boolean> hypotheticalRemoved;

    /**
     * Default constructor. Initializes {@link Optional}s.
     * @param client The owner of the {@link Figure}.
     */
    public Figure(Client client) {
        super(client);
        hypotheticalPosition = Optional.empty();
        hypotheticalRemoved = Optional.empty();
    }

    /**
     * Returns a list of possible {@link Field}s the figure can move to. This list may include {@link Field}s from {@link #getPossibleSpecialMovements(Chessboard)}
     * The List will overlap with {@link #getAttackableFields(Chessboard chessboard)}, but due to some chess rules not all must be included.
     * @param chessboard The instance of the {@link Chessboard} of the current {@link Game}
     * @return Returns a list of {@link Field}.
     */
    public abstract List<T> getPossibleMovements(Chessboard chessboard);

    /**
     * Returns a {@link List} of special movements like en passant, castling, ...
     * The value is distinct to {@link #getPossibleMovements(Chessboard)}, but will be combined in {@link Chessboard#getPossibleFieldsToMove(String)}.
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a list of possible {@link Field}s for special movements like castling, en passant and pawn promotion.
     */
    public abstract List<T> getPossibleSpecialMovements(Chessboard chessboard);

    /**
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return This method returns a list of all fields the figure could attack.
     */
    public abstract List<T> getAttackableFields(Chessboard chessboard);

    /**
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return This method returns a list of all fields the figure could attack with the hypothetical layout.
     */
    public abstract List<T> getHypotheticalAttackableFields(Chessboard chessboard);

    /**
     * Returns the hypothetical position or the actual position, if there is no hypothetical one set.
     * @return Returns a {@link T Field}
     */
    public T getHypotheticalPosition() {
        return hypotheticalPosition.orElse(getPosition());
    }

    /**
     * Set a hypothetical position or use NULL to reset the field.
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
        return hypotheticalRemoved.orElse(isRemoved());
    }

    /**
     * Set if the figure is hypothetically removed or use NULL to reset the field.
     * @param isRemoved If the figure is hypothetically removed or null
     */
    public void setHypotheticalRemoved(Boolean isRemoved) {
        hypotheticalRemoved = Optional.ofNullable(isRemoved);
    }
}
