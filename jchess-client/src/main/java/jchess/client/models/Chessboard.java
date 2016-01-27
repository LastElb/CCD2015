package jchess.client.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Ignore properties field
 */
@JsonIgnoreProperties({"fields"})

/**
 * Model of the Chessboard
 */
public class Chessboard {
    List<Figure> figures;

    /**
     * Initialize Chessboard Model
     */
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

    /**
     * Get List of List of {@link Figure} on the Chessboard
     * @return List of {@link Figure}
     */
    public List<Figure> getFigures() {
        return figures;
    }

    /**
     * Set List of List of {@link Figure} on the Chessboard
     */
    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }
}
