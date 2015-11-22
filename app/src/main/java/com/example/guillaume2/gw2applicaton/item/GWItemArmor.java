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

    public int defense;

    //GWItemSubObject subObject;
    public String suffix_item_id;
    public String secondary_suffix_item_id;
    public TYPE type;
    public WEIGHT_CLASS weight_class;

    public GWItemArmor(JSONObject jo) {
        super.typeObject = GWITEM_TYPE.ARMOR;
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
