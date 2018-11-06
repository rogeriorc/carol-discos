package com.carol.discos.caroldiscos.db;

import android.provider.BaseColumns;

public class DiscEntry implements BaseColumns {
    public static final String TABLE_NAME = "album";
    public static final String COLUMN_NAME_ID = _ID;
    public static final String COLUMN_NAME_NAME = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";

    public long id;
    public String name;
    public String description;
}