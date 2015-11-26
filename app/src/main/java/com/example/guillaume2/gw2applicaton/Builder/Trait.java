package com.example.guillaume2.gw2applicaton.Builder;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 24/11/15.
 */
public class Trait {

    public enum SLOT {
        MAJOR,
        MINOR
    }
    public int id;
    public int tier;
    public String name;
    public String description;
    public SLOT slot;
    public int specialization;
    public List<TraitFact> traits;
    public List<TraitFact> traited_facts;
    public String iconPath;
    public String iconUrl;


    public Trait(int id) {
        traits = new ArrayList<>();
        traited_facts = new ArrayList<>();
        this.id = id;
        iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/spe/trait/image/" + id + "-icon.png";
    }

    public boolean iconExists() {
        return new File(iconPath).exists();
    }


}
