package com.faren.gw2.gw2applicaton;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.faren.gw2.gw2applicaton.dyeDisplay.GWDyeDisplay;
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

    public List<GWDyeDisplay> selectDyes(String name) {
        List<GWDyeDisplay> gwDyeDisplays = new ArrayList<>();
        List<String> whereArgs = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] tableColumns = new String[]{
                item_name,
                "rgbCloth",
                "rgbLeather",
                "rgbMetal",
                "rid"
        };

        String orderBy = "name";

        String select = "Select gwitem.name, rgbCloth, rgbLeather, rgbMetal, trading_value from gwdye, gwitem where name like \""+name+"%\" and " +
                "gwitem.rid = gwdye.rid";
        Cursor c = sqLiteDatabase.rawQuery(select, null);
        System.out.println(c.getCount());
        while (c.moveToNext()) {
            gwDyeDisplays.add(new GWDyeDisplay(c.getString(0),
                    c.getString(1), c.getString(2), c.getString(3), c.getString(4)));
        }

        sqLiteDatabase.close();
        return gwDyeDisplays;
    }

    public List<GWItemInfoDisplay> selectItem(String name, String levelMin,
                                              String levelMax, String vendor_valueMin,
                                              String vendor_valueMax, List<String> rarity,
                                              List<String> types, String limit
    ) {
        List<GWItemInfoDisplay> gwItemInfoDisplays = new ArrayList<>();
        List<String> whereArgs = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] tableColumns = new String[]{
                item_name,
                "type",
                "level",
                "trading_value",
                "rarity",
                "description",
                "vendor_value",
                "iconUrl",
                "rid"
        };
        String whereClause = "";
        if(!"".equals(name)) {
            whereArgs.add(name + "%");
            whereClause = "name LIKE ? and";
        }

        whereClause += " level >= ?";
        whereArgs.add(levelMin);
        whereClause += " and level <= ?";
        whereArgs.add(levelMax);

        whereClause += " and trading_value >= ?";
        whereArgs.add(vendor_valueMin);
        whereClause += " and trading_value <= ?";
        whereArgs.add(vendor_valueMax);

        if (rarity.size() > 0) {
            whereClause += " and ( rarity = ?";
            whereArgs.add(rarity.get(0));
            for (int i = 1; i < rarity.size(); i++) {
                whereClause += " or rarity = ?";
                whereArgs.add(rarity.get(i));
            }
            whereClause += ")";
        }

        if (types.size() > 0) {
            whereClause += " and ( type = ?";
            whereArgs.add(types.get(0));
            for (int i = 1; i < types.size(); i++) {
                whereClause += " or type = ?";
                whereArgs.add(types.get(i));
            }
            whereClause += ")";
        }

        String[] args = new String[whereArgs.size()];
        String orderBy = "name";
        Cursor c = sqLiteDatabase.query(table_item, tableColumns, whereClause, whereArgs.toArray(args),
                null, null, orderBy, limit);
        System.out.println(c.getCount());
        while (c.moveToNext()) {
            gwItemInfoDisplays.add(new GWItemInfoDisplay(c.getString(0),
                    c.getString(1), c.getString(2), c.getString(3), c.getString(4),
                    c.getString(5), c.getString(6), c.getString(7),
                    c.getString(8)));
        }

        sqLiteDatabase.close();
        return gwItemInfoDisplays;
    }
}

