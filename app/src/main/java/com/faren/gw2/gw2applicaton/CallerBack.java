package com.faren.gw2.gw2applicaton;


public interface CallerBack {
    void notifyUpdate(Object ...o);
    void cancel();
}
