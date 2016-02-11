package com.faren.gw2.gw2applicaton.dyeDisplay;

import android.graphics.Color;

/**
 * Created by guillaume2 on 06/02/16.
 */
public class GWDyeDisplay {
    public String name;
    public Color rgbCloth;
    public Color rgbMetal;
    public Color rgbLeather;
    public String trading_value;

    public GWDyeDisplay(String name, String rgbCloth, String rgbMetal, String rgbLeather, String trading_value) {
        this.name = name;
        this.trading_value = trading_value;
        this.rgbCloth = toColor(rgbCloth);
        this.rgbMetal = toColor(rgbMetal);
        this.rgbLeather = toColor(rgbLeather);
    }

    private Color toColor(String color) {
        return new Color();
    }

}
