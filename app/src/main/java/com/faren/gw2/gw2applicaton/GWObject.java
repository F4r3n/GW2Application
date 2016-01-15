package com.faren.gw2.gw2applicaton;

public abstract class GWObject implements CallerBack, DataUpdater {

    protected Categories cat;
    protected String url;
    protected String directoryFile = "/GW2App/";
    protected String directoryName = "bank.data";

    public GWObject() {
    }

     public Categories getCat() {
        return cat;
    }

    public String getUrl() {
        return url;
    }

    protected void readFile(CallerBack parent, String results) {
    }

    abstract protected void deleteFileData();

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
