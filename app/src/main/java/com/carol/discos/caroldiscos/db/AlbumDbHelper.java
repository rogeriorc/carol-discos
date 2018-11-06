package com.carol.discos.caroldiscos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AlbumDbHelper  extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "album.db";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + AlbumEntry.TABLE_NAME + " (" +
                        AlbumEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                        AlbumEntry.COLUMN_NAME_TITLE + " TEXT," +
                        AlbumEntry.COLUMN_NAME_ARTIST + " TEXT," +
                        AlbumEntry.COLUMN_NAME_YEAR + " INTEGER," +
                        AlbumEntry.COLUMN_NAME_GENRE + " INTEGER)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + AlbumEntry.TABLE_NAME;


        public AlbumDbHelper(Context context) {
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

        public long insert(String title, String artist, int year, long genre) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(AlbumEntry.COLUMN_NAME_TITLE, title);
            values.put(AlbumEntry.COLUMN_NAME_ARTIST, artist);
            values.put(AlbumEntry.COLUMN_NAME_YEAR, year);
            values.put(AlbumEntry.COLUMN_NAME_GENRE, genre);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(AlbumEntry.TABLE_NAME, null, values);

            db.close();

            return newRowId;
        }

        public boolean update(long id, String title, String artist, int year, long genre) {
            SQLiteDatabase db = this.getWritableDatabase();

            // New value for one column
            ContentValues values = new ContentValues();
            values.put(AlbumEntry.COLUMN_NAME_TITLE, title);
            values.put(AlbumEntry.COLUMN_NAME_ARTIST, artist);
            values.put(AlbumEntry.COLUMN_NAME_YEAR, year);
            values.put(AlbumEntry.COLUMN_NAME_GENRE, genre);

            // Which row to update, based on the title
            String selection = AlbumEntry.COLUMN_NAME_ID + " = ?";
            String[] selectionArgs = { String.valueOf(id) };

            int count = db.update(
                    AlbumEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);


            db.close();

            return (count > 0);
        }

        public boolean delete(long id) {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = AlbumEntry.COLUMN_NAME_ID + " = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = { String.valueOf(id) };

            // Issue SQL statement.
            int deletedRows = db.delete(AlbumEntry.TABLE_NAME, selection, selectionArgs);

            db.close();

            return (deletedRows > 0);
        }


        public ArrayList<AlbumEntry> select() {
            ArrayList<AlbumEntry> items = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    AlbumEntry.COLUMN_NAME_ID,
                    AlbumEntry.COLUMN_NAME_TITLE,
                    AlbumEntry.COLUMN_NAME_ARTIST,
                    AlbumEntry.COLUMN_NAME_YEAR,
                    AlbumEntry.COLUMN_NAME_GENRE,
            };

            // Filter results WHERE "title" = 'My Title'
            //String selection = AlbumEntry.COLUMN_NAME_TITLE + " = ?";
            //String[] selectionArgs = {"My Title"};

            // How you want the results sorted in the resulting Cursor
            String sortOrder = AlbumEntry.COLUMN_NAME_TITLE + " DESC";

            Cursor cursor = db.query(
                    AlbumEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,   //selection,              // The columns for the WHERE clause
                    null, //selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder               // The sort order
            );

            while (cursor.moveToNext()) {
                AlbumEntry item = new AlbumEntry();

                item.id = cursor.getLong(
                        cursor.getColumnIndexOrThrow(AlbumEntry.COLUMN_NAME_ID)
                );

                item.title = cursor.getString(
                        cursor.getColumnIndexOrThrow(AlbumEntry.COLUMN_NAME_TITLE)
                );

                item.artist = cursor.getString(
                        cursor.getColumnIndexOrThrow(AlbumEntry.COLUMN_NAME_ARTIST)
                );

                item.year = cursor.getInt(
                        cursor.getColumnIndexOrThrow(AlbumEntry.COLUMN_NAME_YEAR)
                );

                item.genre = cursor.getLong(
                        cursor.getColumnIndexOrThrow(AlbumEntry.COLUMN_NAME_GENRE)
                );

                items.add(item);
            }

            cursor.close();

            return items;
        }

    }

