package jchess.client.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Ignore the attribute atEnemyBaseline if set
 */
@JsonIgnoreProperties({"atEnemyBaseline"})

/**
 * Model of the Figures
 */
public class Figure {
    String id;
    String name;
    String pictureId;
    Boolean removed;
    Position position;

    /**
     * Get the unique identifier of the figure
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the unique identifier of the figure
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the name of the figure e.g. pawn
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the chess figure
     * @param name Name of the figure
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name of the picture, which represents the figure
     * @return name of an image
     */
    public String getPictureId() {
        return pictureId;
    }

    /**
     * Set the name of the picture, which represents the figure
     * @param pictureId
     */
    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    /**
     * Check whether the figure was removed form the chessboard
     * @return Boolean
     */
    public Boolean getRemoved() {
        return removed;
    }

    /**
     * Set the status, whether the figure was removed from the chessboard
     * @param removed
     */
    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    /**
     * Get the Position of the figure
     * @return Object of type {@link Position}
     */
    @JsonProperty("position")
    public Position getPositionObject() {
        return position;
    }

    /**
     * Set the position of the figure passing an {@link Position} object
     * @param position
     */
    public void setPositionObject(Position position) {
        this.position = position;
    }
}
