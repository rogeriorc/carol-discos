package com.carol.discos.caroldiscos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Baseado nos exemplos em https://developer.android.com/training/data-storage/sqlite
 */


public class GenderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gender.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GenderEntry.TABLE_NAME + " (" +
                    GenderEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    GenderEntry.COLUMN_NAME_TITLE + " TEXT," +
                    GenderEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GenderEntry.TABLE_NAME;


    public GenderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insert(String name, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GenderEntry.COLUMN_NAME_TITLE, name);
        values.put(GenderEntry.COLUMN_NAME_DESCRIPTION, desc);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(GenderEntry.TABLE_NAME, null, values);

        db.close();

        return newRowId;
    }

    public boolean update(long id, String name, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(GenderEntry.COLUMN_NAME_TITLE, name);
        values.put(GenderEntry.COLUMN_NAME_DESCRIPTION, desc);

        // Which row to update, based on the title
        String selection = GenderEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(
                GenderEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);


        db.close();

        return (count > 0);
    }

    public boolean delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GenderEntry.COLUMN_NAME_ID + " = ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };

        // Issue SQL statement.
        int deletedRows = db.delete(GenderEntry.TABLE_NAME, selection, selectionArgs);

        db.close();

        return (deletedRows > 0);
    }


    public ArrayList<GenderEntry> select() {
        ArrayList<GenderEntry> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                GenderEntry.COLUMN_NAME_ID,
                GenderEntry.COLUMN_NAME_TITLE,
                GenderEntry.COLUMN_NAME_DESCRIPTION
        };

        // Filter results WHERE "title" = 'My Title'
        //String selection = GenderEntry.COLUMN_NAME_TITLE + " = ?";
        //String[] selectionArgs = {"My Title"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = GenderEntry.COLUMN_NAME_TITLE + " DESC";

        Cursor cursor = db.query(
                GenderEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,   //selection,              // The columns for the WHERE clause
                null, //selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        while (cursor.moveToNext()) {
            GenderEntry item = new GenderEntry();

            item.id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(GenderEntry.COLUMN_NAME_ID)
            );

            item.title = cursor.getString(
                    cursor.getColumnIndexOrThrow(GenderEntry.COLUMN_NAME_TITLE)
            );

            item.description = cursor.getString(
                    cursor.getColumnIndexOrThrow(GenderEntry.COLUMN_NAME_DESCRIPTION)
            );

            items.add(item);
        }

        cursor.close();

        return items;
    }

}
