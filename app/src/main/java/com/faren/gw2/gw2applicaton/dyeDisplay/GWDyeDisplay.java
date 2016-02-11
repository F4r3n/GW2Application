package com.faren.gw2.gw2applicaton.dyeDisplay;

import android.graphics.Color;

public class GWDyeDisplay {
    public String name;
    public int rgbCloth;
    public int rgbMetal;
    public int rgbLeather;
    public String trading_value;

    public GWDyeDisplay(String name, String rgbCloth, String rgbMetal, String rgbLeather, String trading_value) {
        this.name = name;
        this.trading_value = trading_value;
        this.rgbCloth = toColor(rgbCloth);
        this.rgbMetal = toColor(rgbMetal);
        this.rgbLeather = toColor(rgbLeather);
    }

    private int toColor(String color) {
        String[] colorSplit = color.replace("[", "").replace("]", "").replace(" ","").split(",");
        return Color.rgb(Integer.parseInt(colorSplit[0]), Integer.parseInt(colorSplit[1]), Integer.parseInt(colorSplit[2]));
    }

}
