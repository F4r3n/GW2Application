package com.example.guillaume2.gw2applicaton;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by guillaume2 on 01/11/15.
 */
public class RequestManager extends Observable {

    private List<Container> containers = new ArrayList<Container>();
    int index = 0;
    private CATEGORIES param;

    public RequestManager() {
        containers.add(new Container());
    }

    public void overNotify() {
        clearChanged();
    }

    public void notifyFinish(CATEGORIES param, Object cont) {
        switch (param) {
            case ACCOUNT:
                containers.get(0).setAccount((Account) cont);
                break;
            case DYES:
                break;
            case SKINS:
                break;
            case PVP:
                break;
            case BANK:
                break;
        }

        setChanged();
        notifyObservers(param);
    }

    public Object getContainer(CATEGORIES cat) {
        switch (cat) {
            case ACCOUNT:
                return containers.get(0).getAccount();
            case DYES:
                break;
            case SKINS:
                break;
            case PVP:
                break;
            case BANK:
                break;
        }
        return null;
    }

    public void execute(CATEGORIES cat) {
        Request r = new Request(this);

        r.execute(cat);

    }

}
