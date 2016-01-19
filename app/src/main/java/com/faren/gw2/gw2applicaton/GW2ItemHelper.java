package com.faren.gw2.gw2applicaton;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.faren.gw2.gw2applicaton.item.GWItem;


public class GW2ItemHelper extends SQLiteOpenHelper {

    private static int database_VERSION = 1;
    private static final String database_NAME = "itemsDB";
    private static final String table_item = "gwitem";
    private static final String item_id = "id";
    private static final String item_rid = "rid";
    private static final String item_name = "name";

    public GW2ItemHelper(Context context) {
        super(context, table_item, null, database_VERSION);
    }

    public GW2ItemHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + table_item + " ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " rid INTEGER, name TEXT)";
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_item );
        database_VERSION = newVersion;
        this.onCreate(db);
    }

    public void createGWItem(GWItem item) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(item_rid, Integer.parseInt(item.gwItemData.id));
        values.put(item_name, item.gwItemData.name);
        db.insert(table_item, null, values);

        db.close();
    }

    public void createGWItem(String id,String name, String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(item_rid, Integer.parseInt(id));
        db.insert(table_item, null, values);

        db.close();
    }

}
