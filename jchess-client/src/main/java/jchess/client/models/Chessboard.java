package jchess.client.models;

import java.util.List;

public class Chessboard {
    List<Figure> figures;

    /**
     * Default constructor to create a new Chessboard Model
     * @param figures
     */
    public Chessboard(List<Figure> figures) {
        this.figures = figures;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }
}
