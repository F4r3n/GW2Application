package com.example.guillaume2.gw2applicaton.item;

import android.graphics.Bitmap;

import com.example.guillaume2.gw2applicaton.CallerBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 10/11/15.
 */
public class GWItem implements CallerBack {
    String id;
    String url = "https://api.guildwars2.com/v2/items/";
    String chat_link;
    public String name;
    public String icon;
    String description;
    GWITEM_TYPE type;
    GWITEM_RARITY rarity;
    List<GWITEM_FLAGS> flags;
    List<GWITEM_GAMETYPES> game_types;
    List<GWITEM_RESTRICTIONS> restrictions;
    int level;
    int vendor_value;
    String default_skin;
    GWItemDetailObject object;
    transient private CallerBack callerBack;
    public Bitmap image;
    transient public static final int MAX_WIDTH = 20;
    transient public static final int MAX_HEIGHT = 20;


    public GWItem(CallerBack cb, String id) {
        callerBack = cb;
        this.id = id;
        game_types = new ArrayList<>();
        flags = new ArrayList<>();
        restrictions = new ArrayList<>();


    }

    public void readFile(String result) throws JSONException {
        JSONObject reader = new JSONObject(result);
        name = reader.getString("name");
        type = GWITEM_TYPE.valueOf(reader.getString("type").toUpperCase());
        level = reader.getInt("level");
        rarity = GWITEM_RARITY.valueOf(reader.getString("rarity").toUpperCase());
        //System.out.println(rarity);
        vendor_value = reader.getInt("vendor_value");
        JSONArray game_typesJSON = reader.getJSONArray("game_types");
        for (int i = 0; i < game_typesJSON.length(); i++) {
            game_types.add(GWITEM_GAMETYPES.valueOf(game_typesJSON.getString(i).toUpperCase()));
        }
        JSONArray flagsJSON = reader.getJSONArray("flags");
        for (int i = 0; i < flagsJSON.length(); i++) {
            flags.add(GWITEM_FLAGS.valueOf(flagsJSON.getString(i).toUpperCase()));
        }
        JSONArray restrictionsJSON = reader.getJSONArray("restrictions");
        for (int i = 0; i < restrictionsJSON.length(); i++) {
            restrictions.add(GWITEM_RESTRICTIONS.valueOf(restrictionsJSON.getString(i).toUpperCase()));
        }
        chat_link = reader.getString("chat_link");
        icon = reader.getString("icon");
        System.out.println("icon " + icon);
        //new DownloadImage(this).execute(icon);
        callerBack.notifyUpdate(this, 0.0f, name);

    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }


}
