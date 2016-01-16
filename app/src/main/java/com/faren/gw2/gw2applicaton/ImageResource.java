package com.faren.gw2.gw2applicaton;

public class ImageResource {
    public String name;
    public String iconUrl;
    public String iconPath;
    public int width;
    public int height;

    public ImageResource(String name, String iconUrl, String iconPath, int width, int height) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.iconPath = iconPath;
        this.width = width;
        this.height = height;
    }

    public ImageResource(int w, int h) {
        this.width = w;
        this.height = h;
    }

}
