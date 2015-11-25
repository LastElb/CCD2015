package de.mki.jchess.server.implementation.twoPersonChess;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.server.model.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Igor on 11.11.2015.
 */
public class Square extends Field<Direction> {

    List<SquareNeighbourModel> neighbours;
    int x;
    int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.neighbours = new ArrayList<>();
    }

    @Override
    public String getNotation() {
        String letter;
        switch (x){
            case 0: letter = "a"; break;
            case 1: letter = "b"; break;
            case 2: letter = "c"; break;
            case 3: letter = "d"; break;
            case 4: letter = "e"; break;
            case 5: letter = "f"; break;
            case 6: letter = "g"; break;
            case 7: letter = "h"; break;
            default:letter = "";
        }
        return letter + (y + 1);
    }

    @Override
    public void addNeighbour(Field neighbour, Direction direction) {
        if (neighbours.stream().filter(squareNeighbourModel -> squareNeighbourModel.direction.equals(direction)).count() == 0)
            neighbours.add(new SquareNeighbourModel((Square) neighbour, direction));
    }

    public Optional<Square> getNeighbourByDirection(Direction direction) {
        return neighbours.stream().filter(squareNeighbourModel -> squareNeighbourModel.direction.equals(direction)).map(SquareNeighbourModel::getSquare).findFirst();
    }

    @JsonIgnore
    public List<SquareNeighbourModel> getNeighbours() {
        return neighbours;
    }

    public static class SquareNeighbourModel {
        Square square;
        Direction direction;

        public SquareNeighbourModel(Square square, Direction direction) {
            this.square = square;
            this.direction = direction;
        }

        public Square getSquare() {
            return square;
        }

        public SquareNeighbourModel setSquare(Square square) {
            this.square = square;
            return this;
        }

        public Direction getDirection() {
            return direction;
        }

        public SquareNeighbourModel setDirection(Direction direction) {
            this.direction = direction;
            return this;
        }
    }
}
