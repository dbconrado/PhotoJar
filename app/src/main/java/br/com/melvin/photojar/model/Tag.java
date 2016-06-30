package br.com.melvin.photojar.model;

/**
 * Created by DB on 30/06/2016.
 */
public class Tag {
    private String name;
    private int color;

    public Tag(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public Tag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}
