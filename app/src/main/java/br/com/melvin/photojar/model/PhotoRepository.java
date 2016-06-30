package br.com.melvin.photojar.model;

import java.util.List;

/**
 * Created by DB on 30/06/2016.
 */
public interface PhotoRepository {

    List<Tag> getAllTags();
    List<Photo> getPhotosFromTag(String tagName);
    void addPhotoWithTags(Photo photo);
}
