package com.example.guillaume2.gw2applicaton.item;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guillaume2 on 10/11/15.
 */
public class GWItemArmor extends GWItemDetailObject {
    public enum TYPE {
        BOOTS,
        COAT,
        GLOVES,
        HELM,
        HELMAQUATIC,
        LEGGINGS,
        SHOULDERS,
    }

    public enum WEIGHT_CLASS {
        HEAVY,
        MEDIUM,
        LIGHT,
        CLOTHING
    }

    int defense;
    //GWItemSubObject subObject;
    String suffix_item_id;
    String secondary_suffix_item_id;
    TYPE type;
    WEIGHT_CLASS weight_class;

    public GWItemArmor(JSONObject jo) {
        try {
            readFile(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void readFile(JSONObject jo) throws JSONException {
        type = TYPE.valueOf(jo.getString("type").toUpperCase());
        weight_class = WEIGHT_CLASS.valueOf(jo.getString("weight_class").toUpperCase());
        defense = jo.getInt("defense");

    }
}
