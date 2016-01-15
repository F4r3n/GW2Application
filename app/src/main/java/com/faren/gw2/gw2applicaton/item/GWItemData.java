package com.faren.gw2.gw2applicaton.item;

import android.os.Environment;

import com.faren.gw2.gw2applicaton.ImageResource;

import java.util.ArrayList;
import java.util.List;

public class GWItemData {
    public String id;
    public String chat_link;
    public String name;
    public String description = "";
    public GWItem_Type type;
    public GWItem_Rarity rarity;
    public List<GWItem_flags> flags;
    public List<GWItem_Gametypes> game_types;
    public List<GWItem_Restrictions> restrictions;
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
