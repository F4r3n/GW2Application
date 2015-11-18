package com.example.guillaume2.gw2applicaton.item;

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
    public String id;
    public String url = "https://api.guildwars2.com/v2/items/";
    public String chat_link;
    public String name;
    public String iconUrl;
    public String description="";
    public GWITEM_TYPE type;
    public GWITEM_RARITY rarity;
    public List<GWITEM_FLAGS> flags;
    public List<GWITEM_GAMETYPES> game_types;
    public List<GWITEM_RESTRICTIONS> restrictions;
    public int level;
    public int vendor_value;
    public String default_skin;
    public GWItemDetailObject object;
    transient private CallerBack callerBack;
    public String imagePath;
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
        iconUrl = reader.getString("icon");
        System.out.println("icon " + iconUrl);
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
