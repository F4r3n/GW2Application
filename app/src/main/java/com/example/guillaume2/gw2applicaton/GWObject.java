package com.example.guillaume2.gw2applicaton;

/**
 * Created by guillaume2 on 04/11/15.
 */
public class GWObject implements CallerBack{
    protected CATEGORIES cat;
    protected String url;

    public GWObject() {}
    public CATEGORIES getCat() {
        return cat;
    }

    public String getUrl() {
        return url;
    }

     protected void readFile(CallerBack parent, String results){};

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }


}
