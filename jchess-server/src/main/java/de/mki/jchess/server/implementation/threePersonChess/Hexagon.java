package de.mki.jchess.server.implementation.threePersonChess;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.commons.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Hexagon is representing the field type to play with this three person chess.
 * Created by Igor on 13.11.2015.
 */
public class Hexagon extends Field<Direction> {

    List<HexagonNeighbourModel> neighbours;
    int column;
    int row;

    /**
     * Default constructor
     * @param column The column of the {@link Hexagon}.
     * @param row The row of the {@link Hexagon}.
     */
    public Hexagon(int column, int row) {
        this.column = column;
        this.row = row;
        this.neighbours = new ArrayList<>();
    }

    /**
     * Notation is taken from http://www.dreierschach.de/pics/bezeichnung.gif
     * @return Returns the notation
     */
    @Override
    public String getNotation() {
        String letter;
        switch (row){
            case 0:  letter = "a"; break;
            case 1:  letter = "b"; break;
            case 2:  letter = "c"; break;
            case 3:  letter = "d"; break;
            case 4:  letter = "e"; break;
            case 5:  letter = "f"; break;
            case 6:  letter = "g"; break;
            case 7:  letter = "h"; break;
            case 8:  letter = "i"; break;
            case 9:  letter = "j"; break;
            case 10: letter = "k"; break;
            case 11: letter = "l"; break;
            case 12: letter = "m"; break;
            default: letter = "";
        }
        return letter + (column + 1);
    }

    /**
     * {@inheritDoc}
     * @param neighbour The neighbour relative to the current {@link Field}.
     * @param direction The direction where this neighbour {@link Field} is located.
     */
    @Override
    public void addNeighbour(Field neighbour, Direction direction) {
        if (neighbours.stream().filter(hexagonNeighbourModel -> hexagonNeighbourModel.direction.equals(direction)).count() == 0)
            neighbours.add(new HexagonNeighbourModel((Hexagon) neighbour, direction));
    }

    /**
     * Returns the neighbour of a specific {@link Direction}.
     * @param direction The desired {@link Direction} where the neighbour is located.
     * @return Returns an {@link Optional} of a {@link Hexagon}. If {@link Hexagon} exists in the {@link Direction} of
     * the parameter, the {@link Optional} contains a value, else it is empty.
     */
    public Optional<Hexagon> getNeighbourByDirection(Direction direction) {
        return neighbours.stream().filter(hexagonNeighbourModel -> hexagonNeighbourModel.direction.equals(direction)).map(HexagonNeighbourModel::getHexagon).findFirst();
    }

    /**
     * Static class used to save the connection between {@link Direction} and neighbour {@link Hexagon}.
     */
    public static class HexagonNeighbourModel {
        Hexagon hexagon;
        Direction direction;

        /**
         * Default constructor.
         * @param hexagon The neighbour {@link Hexagon}.
         * @param direction The {@link Direction} the {@link Hexagon} is located in.
         */
        public HexagonNeighbourModel(Hexagon hexagon, Direction direction) {
            this.hexagon = hexagon;
            this.direction = direction;
        }

        /**
         * Returns the {@link Hexagon} of this model.
         * @return Returns the {@link Hexagon} of this model.
         */
        public Hexagon getHexagon() {
            return hexagon;
        }
    }

    /**
     * Checks if this {@link Hexagon} is neighbour of another {@link Hexagon}.
     * @param neighbour    The neighbour to check for
     * @return Returns true if the specified {@link Hexagon} is a neighbour of this {@link Hexagon}.
     */
    public boolean isHexagonNeighbour(Hexagon neighbour) {
        return neighbours.stream()
                .filter(hexagonNeighbourModel -> hexagonNeighbourModel.getHexagon().equals(neighbour))
                .findAny().isPresent();
    }

    /**
     * Gets the column number.
     * @return Returns the column number in the interval [0,12].
     */
    @JsonIgnore
    public int getColumn() {
        return column;
    }

    /**
     * Gets the row number.
     * @return Returns the row number in the interval [0,12].
     */
    @JsonIgnore
    public int getRow() {
        return row;
    }

    /**
     * {@inheritDoc}
     * @return Returns a formatted {@link String} with row, column and notation.
     */
    @Override
    public String toString() {
        return "Hexagon{" +
                "row=" + row +
                ", column=" + column +
                ", notation=" + getNotation() +
                '}';
    }
}
