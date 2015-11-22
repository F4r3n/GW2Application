package com.example.guillaume2.gw2applicaton.item;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guillaume2 on 19/11/15.
 */
public class GWItemConsumable extends GWItemDetailObject {
    public enum TYPE {
        APPEARANCECHANGE,
        BOOZE,
        ALCOHOL,
        CONTRACTNPC,
        FOOD,
        GENERIC,
        HALLOWEEN,
        IMMEDIATE,
        TRANSMUTATION,
        UNLOCK,
        UPGRADEREMOVAL,
        UTILITY,
        TELEPORTTOFRIEND

    }

    public enum UNLOCK_TYPE {
        BAGSLOT,
        BANKTAB,
        COLLECTIBLECAPACITY,
        CONTENT,
        CRAFTINGRECIPE,
        DYE,
        UNKNOWN
    }

    public TYPE type;
    public int duration_ms;
    public String color_id;
    public String recipe_id;

    public GWItemConsumable(JSONObject jo) {
        super.typeObject = GWITEM_TYPE.CONSUMABLE;

        try {
            readFile(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void readFile(JSONObject jo) throws JSONException {
        type = TYPE.valueOf(jo.getString("type").toUpperCase());
        if (jo.has("description"))
            description = jo.getString("description");
        if (jo.has("duration_ms"))
            duration_ms = jo.getInt("duration_ms");
        if (jo.has("color_id"))
            color_id = jo.getString("color_id");
        if (jo.has("recipe_id"))
            recipe_id = jo.getString("recipe_id");

    }


}
