package com.faren.gw2.gw2applicaton;


public class DataImageToDl {
    public String id;
    public int index = 0;
    public ImageResource imageResource;

    public DataImageToDl(ImageResource imageResource, String id, int index) {
        this.imageResource = imageResource;
        System.out.println(imageResource.iconPath);
        this.id = id;
        this.index = index;
    }
}
