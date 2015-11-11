package de.mki.jchess.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */
public abstract class Chessboard<T extends Field> {
    public List<Figure> figures;
    public List<T> fields;

    public Chessboard() {
        fields = new ArrayList<>();
        figures = new ArrayList<>();
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public Chessboard setFigures(List<Figure> figures) {
        this.figures = figures;
        return this;
    }

    public List<T> getFields() {
        return fields;
    }

    public Chessboard addField(T field) {
        this.fields.add(field);
        return this;
    }

    public Field getFieldByNotation(String notation) throws Exception {
        return fields.stream().filter(field -> field.getNotation().equals(notation)).findFirst().orElseThrow(() -> new Exception("Notation not found"));
    }
}
