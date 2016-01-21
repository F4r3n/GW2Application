package com.faren.gw2.gw2applicaton;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.faren.gw2.gw2applicaton.item.GWItemInfoDisplay;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class GW2ItemHelper extends SQLiteAssetHelper {

    private static int database_VERSION = 1;
    private static final String DB_NAME = "gw2item.db";
    private static final String table_item = "gwitem";
    private static final String item_id = "id";
    private static final String item_rid = "rid";
    private static final String item_name = "name";
    private static final String item_description = "description";


    private final Context myContext;


    public GW2ItemHelper(Context context) {
        super(context, DB_NAME, null, database_VERSION);
        this.myContext = context;
    }

    public List<GWItemInfoDisplay> selectItem(String name) {
        List<GWItemInfoDisplay> gwItemInfoDisplays = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] tableColumns = new String[]{
                item_name
        };
        String whereClause = "name LIKE ?";
        String[] whereArgs = new String[]{
                name +"%"
        };

        String orderBy = "name";
        Cursor c = sqLiteDatabase.query(table_item, tableColumns, whereClause, whereArgs,
                null, null, orderBy);
        System.out.println(c.getCount());
        while (c.moveToNext()) {
            gwItemInfoDisplays.add(new GWItemInfoDisplay(c.getString(0)));
        }

        sqLiteDatabase.close();
        return gwItemInfoDisplays;

    }


}

