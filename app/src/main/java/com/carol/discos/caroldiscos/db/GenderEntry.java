package com.carol.discos.caroldiscos.db;

import android.provider.BaseColumns;

public class GenderEntry implements BaseColumns {
    public static final String TABLE_NAME = "gender";
    public static final String COLUMN_NAME_ID = _ID;
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";

    public long id;
    public String title;
    public String description;

    public GenderEntry() {
        id = 0;
        title= "";
        description = "";
    }


    @Override
    public String toString() {
        return title;
    }
}
