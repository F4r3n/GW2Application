package com.faren.gw2.gw2applicaton.item;


public class GWItemInfoDisplay {
    public String name;
    public String type;
    public String level;
    public String vendor_value;
    public String trading_value;
    public String rarity;
    public String description;
    public String iconUrl;
    public String rid;

    public GWItemInfoDisplay(String name, String type, String level,
                             String trading_value, String rarity,
                             String description, String vendor_value,
                             String iconUrl, String rid
    ) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.vendor_value = vendor_value;
        this.rarity = rarity;
        this.description = description;
        this.trading_value = trading_value;
        this.iconUrl = iconUrl;
        this.rid = rid;
    }

}
