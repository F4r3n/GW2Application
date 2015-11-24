package com.example.guillaume2.gw2applicaton;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 17/11/15.
 */
public class Collection extends Application {
    int index = 0;
    List<Container> containers;
    RequestManager requestManager;
    public SpecializationManager specializationManager;

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
