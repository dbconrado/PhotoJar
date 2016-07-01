package br.com.melvin.photojar.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DB on 30/06/2016.
 *
 * It seems a good practice to keep just one OpenHelper open
 * for the entire app, so I applied the Singleton pattern here.
 */
public class PhotoJarDBHelper extends SQLiteOpenHelper {

    private static PhotoJarDBHelper instance;

    public static synchronized PhotoJarDBHelper getHelper(Context context) {
        if (instance == null)
            instance = new PhotoJarDBHelper(context);
        return instance;
    }

    private static final String DATABASE_NAME = "photojar.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_PHOTO_TABLE =
            "CREATE TABLE photo (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "timestamp INTEGER NOT NULL," +
                    "path TEXT NOT NULL)";

    private static final String CREATE_TAG_TABLE =
            "CREATE TABLE tag (" +
                    "name TEXT PRIMARY KEY," +
                    "color INTEGER NOT NULL)";

    private static final String CREATE_PHOTOTAG_TABLE =
            "CREATE TABLE phototag (" +
                    "photo_id INTEGER PRIMARY KEY," +
                    "tag_name TEXT PRIMARY KEY)";

    private PhotoJarDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PHOTO_TABLE);
        sqLiteDatabase.execSQL(CREATE_TAG_TABLE);
        sqLiteDatabase.execSQL(CREATE_PHOTOTAG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS photo");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tag");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS phototag");
        onCreate(sqLiteDatabase);
    }
}
