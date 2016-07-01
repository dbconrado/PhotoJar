package br.com.melvin.photojar.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.melvin.photojar.model.Photo;
import br.com.melvin.photojar.model.PhotoRepository;
import br.com.melvin.photojar.model.Tag;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE;
import static br.com.melvin.photojar.data.PhotoJarDBHelper.getHelper;

/**
 * Created by DB on 30/06/2016.
 * An implementation of PhotoRepository where
 * data is stored into a SQLite database.
 */
public class SQLitePhotoRepository implements PhotoRepository {

    private Context context;

    public SQLitePhotoRepository(Context context) {
        this.context = context;
    }

    @Override
    public List<Tag> getAllTags() {
        String sql = "SELECT name, color FROM tag";

        SQLiteDatabase db = getHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<Tag> result = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext()) {
            result.add(buildTagFromCursor(cursor, 0));
        }

        return result;
    }

    private Tag buildTagFromCursor(Cursor cursor, int index) {
        final Tag tag = new Tag();
        tag.setName(cursor.getString(index++));
        tag.setColor(cursor.getInt(index));
        return tag;
    }

    @Override
    public List<Photo> getPhotosFromTag(String tagName) {
        String sql = "SELECT p.id, p.timestamp, p.path, t.name, t.color " +
                "FROM phototag pt, photo p, tag t " +
                "WHERE pt.photo_id = p.id " +
                "AND pt.tag_name = t.name " +
                "AND t.name = ?";

        SQLiteDatabase db = getHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{tagName});

        List<Photo> result = new ArrayList<>();

        cursor.moveToFirst();
        do {
            int index = 0;
            Photo photo = new Photo();
            photo.setId(cursor.getInt(index++));
            photo.setTimestamp(new Date(cursor.getLong(index++)));
            photo.setPath(cursor.getString(index++));

            do {
                photo.addTag(buildTagFromCursor(cursor, index));
            } while (cursor.moveToNext() && cursor.getInt(0) == photo.getId());

            result.add(photo);
        } while (!cursor.isAfterLast());

        return result;
    }

    @Override
    public void addPhotoWithTags(Photo photo) {
        SQLiteDatabase db = getHelper(context).getWritableDatabase();

        db.beginTransaction();

        // insert photo
        ContentValues photoValues = new ContentValues();
        photoValues.put("timestamp", photo.getTimestamp().getTime());
        photoValues.put("path", photo.getPath());

        db.insert("photo", null, photoValues);

        // insert tags
        for (Tag tag : photo.getTags()) {
            ContentValues tagValues = new ContentValues();
            tagValues.put("name", tag.getName());
            tagValues.put("color", tag.getColor());

            db.insertWithOnConflict("tag", null, tagValues, CONFLICT_IGNORE);

            // insert photo x tag relationship
            ContentValues phototagValues = new ContentValues();
            phototagValues.put("photo_id", photo.getId());
            phototagValues.put("tag_name", tag.getName());

            db.insert("phototag", null, phototagValues);
        }

        db.endTransaction();
    }
}
