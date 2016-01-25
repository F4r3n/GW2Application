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

    public List<GWItemInfoDisplay> selectItem(String name, String level, String vendor_value) {
        List<GWItemInfoDisplay> gwItemInfoDisplays = new ArrayList<>();
        List<String> whereArgs = new ArrayList<>();
        whereArgs.add(name + "%");
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] tableColumns = new String[]{
                item_name,
                "level",
                "vendor_value"
        };
        String whereClause = "name LIKE ?";
        if (!"".equals(level)) {
            whereClause += " and level = ?";
            whereArgs.add(level);
        }

        if (!"".equals(vendor_value)) {
            whereClause += " and vendor_value =?";
            whereArgs.add(vendor_value);
        }

        String[] args = new String[whereArgs.size()];

        String orderBy = "name";
        Cursor c = sqLiteDatabase.query(table_item, tableColumns, whereClause, whereArgs.toArray(args),
                null, null, orderBy);
        System.out.println(c.getCount());
        while (c.moveToNext()) {
            gwItemInfoDisplays.add(new GWItemInfoDisplay(c.getString(0)));
        }

        sqLiteDatabase.close();
        return gwItemInfoDisplays;


    }


}

