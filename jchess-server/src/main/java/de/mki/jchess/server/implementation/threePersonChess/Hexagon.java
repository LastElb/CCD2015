package de.mki.jchess.server.implementation.threePersonChess;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.server.model.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 13.11.2015.
 */
public class Hexagon extends Field<Direction> {

    @JsonIgnore
    List<HexagonNeighbourModel> neighbours;
    int x;
    int y;

    public Hexagon(int x, int y) {
        this.x = x;
        this.y = y;
        this.neighbours = new ArrayList<>();
    }

    /**
     * Notation is taken from http://www.dreierschach.de/pics/bezeichnung.gif
     * @return Returns the notation
     */
    @Override
    public String getNotation() {
        String letter;
        switch (y){
            case 0: letter = "a"; break;
            case 1: letter = "b"; break;
            case 2: letter = "c"; break;
            case 3: letter = "d"; break;
            case 4: letter = "e"; break;
            case 5: letter = "f"; break;
            case 6: letter = "g"; break;
            case 7: letter = "h"; break;
            case 8: letter = "i"; break;
            case 9: letter = "j"; break;
            case 10: letter = "k"; break;
            case 11: letter = "l"; break;
            case 12: letter = "m"; break;
            default:letter = "";
        }
        return letter + (x + 1);
    }

    @Override
    public void addNeighbour(Field neighbour, Direction direction) {
        if (neighbours.stream().filter(hexagonNeighbourModel -> hexagonNeighbourModel.direction.equals(direction)).count() == 0)
            neighbours.add(new HexagonNeighbourModel((Hexagon) neighbour, direction));
    }

    public static class HexagonNeighbourModel {
        Hexagon hexagon;
        Direction direction;

        public HexagonNeighbourModel(Hexagon hexagon, Direction direction) {
            this.hexagon = hexagon;
            this.direction = direction;
        }

        public Hexagon getHexagon() {
            return hexagon;
        }

        public HexagonNeighbourModel setHexagon(Hexagon hexagon) {
            this.hexagon = hexagon;
            return this;
        }

        public Direction getDirection() {
            return direction;
        }

        public HexagonNeighbourModel setDirection(Direction direction) {
            this.direction = direction;
            return this;
        }
    }
}
