package net.lilggamegenius.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.lilggamegenius.popularmovies.TMDB.POJOs.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String FAVORITES_TABLE_NAME = "favorites";
    public static final String FAVORITES_COLUMN_ID = "id";
    public static final String FAVORITES_COLUMN_JSON = "json";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table favorites " +
                        "(id integer primary key, json text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS favorites");
        onCreate(db);
    }

    public boolean insertFavorite(Movie movie) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FAVORITES_COLUMN_JSON, objectMapper.writeValueAsString(movie));
            db.insert(FAVORITES_TABLE_NAME, null, contentValues);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from favorites where id=" + id + "", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, FAVORITES_TABLE_NAME);
    }

    public boolean updateFavorite(Integer id, Movie movie) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FAVORITES_COLUMN_JSON, objectMapper.writeValueAsString(movie));
            db.update(FAVORITES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer deleteFavorite(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("favorites",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public List<Movie> getAllFavoritess() {
        List<Movie> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor res = db.rawQuery("select * from favorites", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(objectMapper.readValue(res.getString(res.getColumnIndex(FAVORITES_COLUMN_JSON)), Movie.class));
                res.moveToNext();
            }
            return array_list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}