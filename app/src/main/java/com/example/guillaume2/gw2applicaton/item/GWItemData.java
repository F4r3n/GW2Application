package com.example.guillaume2.gw2applicaton.item;

import java.util.List;

/**
 * Created by guillaume2 on 19/11/15.
 */
public class GWItemData {
    public String id;
    public String url = "https://api.guildwars2.com/v2/items/";
    public String chat_link;
    public String name;
    public String iconUrl;
    public String description = "";
    public GWITEM_TYPE type;
    public GWITEM_RARITY rarity;
    public List<GWITEM_FLAGS> flags;
    public List<GWITEM_GAMETYPES> game_types;
    public List<GWITEM_RESTRICTIONS> restrictions;
    public int level;
    public int vendor_value;
    public String default_skin;
    public GWItemArmor armorObject;
    public GWItemConsumable consumableObject;
    public String imagePath;
    public String dataPath;

}
