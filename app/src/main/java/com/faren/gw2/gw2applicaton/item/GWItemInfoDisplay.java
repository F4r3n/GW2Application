package com.faren.gw2.gw2applicaton.item;


public class GWItemInfoDisplay {
    public String name;
    public String type;
    public String level;
    public String vendor_value;
    public String rarity;
    public String description;

    public GWItemInfoDisplay(String name, String type, String level,
                             String vendor_value, String rarity, String description) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.vendor_value = vendor_value;
        this.rarity = rarity;
        this.description = description;
    }

}
