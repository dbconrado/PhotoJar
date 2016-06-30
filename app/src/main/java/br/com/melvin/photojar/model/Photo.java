package br.com.melvin.photojar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DB on 30/06/2016.
 */
public class Photo {
    private int id;
    private Date timestamp;
    private String path;
    private List<Tag> tags = new ArrayList<>();

    public Photo(int id, Date timestamp, String path) {
        this.id = id;
        this.timestamp = timestamp;
        this.path = path;
    }

    public Photo() {
        timestamp = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", path='" + path + '\'' +
                '}';
    }
}
