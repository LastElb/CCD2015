package jchess.client.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"atEnemyBaseline"})

/**
 * Created by Malte on 02.12.2015.
 */

public class Figure {
    String id;
    String name;
    String pictureId;
    Boolean removed;
    Position position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    @JsonProperty("position")
    public Position getPositionObject() {
        return position;
    }

    public void setPositionObject(Position position) {
        this.position = position;
    }
}
