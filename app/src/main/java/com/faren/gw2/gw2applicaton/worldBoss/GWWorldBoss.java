package com.faren.gw2.gw2applicaton.worldBoss;


public class GWWorldBoss {

    public String time;
    public String nameBoss;
    public String url;
    public boolean isFollowed = false;

    public GWWorldBoss(String name, String time, String url) {
        this.time = time;
        this.nameBoss = name;
        this.url = url;
    }
}
