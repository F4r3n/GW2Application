package com.faren.gw2.gw2applicaton;

import android.app.Application;

import com.faren.gw2.gw2applicaton.Builder.SpecializationManager;

import java.util.ArrayList;
import java.util.List;


public class Collection extends Application {
    int index = 0;
    List<Container> containers;
    RequestManager requestManager;
    public SpecializationManager specializationManager;
    public String key = "";

    public Collection() {
        containers = new ArrayList<>();
    }

    public void addContainer(Container c) {
        containers.add(c);
        index++;
    }

    public Container getContainer(int index) {
        return containers.get(index);
    }

    public void setRequestManager(RequestManager rqm) {
        requestManager = rqm;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }


}
