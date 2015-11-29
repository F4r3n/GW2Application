package com.example.guillaume2.gw2applicaton;

/**
 * Created by guillaume2 on 28/11/15.
 */
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

    public ImageResource() {}

    public ImageResource(int w, int h) {
        this.width = w;
        this.height = h;
    }

}
