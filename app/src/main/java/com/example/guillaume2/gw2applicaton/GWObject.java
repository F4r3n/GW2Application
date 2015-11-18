package com.example.guillaume2.gw2applicaton;

/**
 * Created by guillaume2 on 04/11/15.
 */
public abstract class GWObject implements CallerBack, DataUpdater {

    protected CATEGORIES cat;
    protected String url;
    protected String directoryFile = "/GW2App/";
    protected String directoryName = "bank.data";

    public GWObject() {
    }

     public CATEGORIES getCat() {
        return cat;
    }

    public String getUrl() {
        return url;
    }

    protected void readFile(CallerBack parent, String results) {
    }

    ;

    @Override
    public void notifyUpdate(Object... o) {
    }


    @Override
    public void cancel() {

    }


    @Override
    abstract public void writeData();


    @Override
    abstract public boolean isExists();

    @Override
    abstract public void readData();

}
