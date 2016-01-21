package com.faren.gw2.gw2applicaton;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class GW2ItemHelper extends SQLiteAssetHelper {

    private static int database_VERSION = 1;
    private static final String DB_NAME = "databases/gw2item.db";
    private static final String table_item = "gwitem";
    private static final String item_id = "id";
    private static final String item_rid = "rid";
    private static final String item_name = "name";
    private static final String item_description = "description";

    private SQLiteDatabase myDataBase;

    private final Context myContext;


    public GW2ItemHelper(Context context) {
        super(context, DB_NAME, null, database_VERSION);
        this.myContext = context;
    }


    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();
    }


}

