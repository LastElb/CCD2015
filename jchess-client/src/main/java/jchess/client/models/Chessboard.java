package jchess.client.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"fields"})

public class Chessboard {
    List<Figure> figures;

    public Chessboard(){
        figures = new ArrayList<>();
    }

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
