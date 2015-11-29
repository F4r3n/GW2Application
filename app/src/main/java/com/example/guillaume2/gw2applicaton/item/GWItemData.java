package com.example.guillaume2.gw2applicaton.item;

import android.os.Environment;

import com.example.guillaume2.gw2applicaton.ImageResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 19/11/15.
 */
public class GWItemData {
    public String id;
    public String chat_link;
    public String name;
    public String description = "";
    public GWITEM_TYPE type;
    public GWITEM_RARITY rarity;
    public List<GWITEM_FLAGS> flags;
    public List<GWITEM_GAMETYPES> game_types;
    public List<GWITEM_RESTRICTIONS> restrictions;
    public int level;
    public int vendor_value;
    public ImageResource imageResource;
    public String default_skin;
    public GWItemConsumable gwItemConsumable;
    public GWItemArmor gwItemArmor;
    public String dataPath;

    public GWItemData() {
        game_types = new ArrayList<>();
        flags = new ArrayList<>();
        restrictions = new ArrayList<>();
    }

    public GWItemData(String id) {
        game_types = new ArrayList<>();
        flags = new ArrayList<>();
        restrictions = new ArrayList<>();
        dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/item/data/" + id + ".json";
        String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/item/images/" + id + ".png";
        imageResource = new ImageResource(id, "", imagePath, 100, 100);

    }

}
