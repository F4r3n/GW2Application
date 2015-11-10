package com.example.guillaume2.gw2applicaton.item;

import org.json.JSONObject;

/**
 * Created by guillaume2 on 10/11/15.
 */
public class GWItemArmor implements GWItemDetailObject  {
    public enum TYPE{
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
    @Override
    public void readFile(JSONObject jo) {

    }
}
