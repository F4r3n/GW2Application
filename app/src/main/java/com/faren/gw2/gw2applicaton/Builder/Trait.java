package com.faren.gw2.gw2applicaton.Builder;

import android.os.Environment;

import com.faren.gw2.gw2applicaton.ImageResource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public ImageResource iconImage = new ImageResource(50,50);

    public Trait(int id) {
        traits = new ArrayList<>();
        traited_facts = new ArrayList<>();
        this.id = id;
        iconImage.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/spe/trait/image/" + id + "-icon.png";
    }

    public boolean iconExists() {
        return new File(iconImage.iconPath).exists();
    }


}
