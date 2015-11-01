package com.example.guillaume2.gw2applicaton;

import java.util.HashMap;

/**
 * Created by guillaume2 on 01/11/15.
 */
public class RequestManager {

    private HashMap<String, Container> containers = new HashMap<String, Container>();

    public RequestManager() {}

    public void execute(String ...params) {

        new Request(containers).execute(params);
    }
}
