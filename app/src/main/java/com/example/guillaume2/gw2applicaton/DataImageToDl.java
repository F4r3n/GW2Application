package com.example.guillaume2.gw2applicaton;

/**
 * Created by guillaume2 on 22/11/15.
 */
public class DataImageToDl {
    public String id;
    public int index = 0;
    public ImageResource imageResource;

    public DataImageToDl(ImageResource imageResource, String id, int index) {
        this.imageResource = imageResource;
        System.out.println(imageResource.iconPath);
        if (imageResource.iconUrl == null) {
            System.out.println("No url");
        }
        this.id = id;
        this.index = index;
    }
}
