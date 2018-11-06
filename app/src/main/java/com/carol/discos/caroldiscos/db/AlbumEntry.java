package com.carol.discos.caroldiscos.db;

import android.provider.BaseColumns;

public class AlbumEntry implements BaseColumns {
    public static final String TABLE_NAME = "album";
    public static final String COLUMN_NAME_ID = _ID;
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_ARTIST = "artist";
    public static final String COLUMN_NAME_YEAR = "year";
    public static final String COLUMN_NAME_GENRE = "genre";

    public long id;
    public String title;
    public String artist;
    public int year;
    public long genre;

}