package com.example.guillaume2.gw2applicaton.Builder;

import com.example.guillaume2.gw2applicaton.ImageResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 25/11/15.
 */
public class SpecializationData {
    public String name;
    public String path;

    public String id;
    public Professions profession;
    public boolean elite = false;
    public List<Trait> majorTraits = new ArrayList<>();
    public List<Trait> minorTraits = new ArrayList<>();
    public ImageResource backgroundImage = new ImageResource(1000,1000);
    public ImageResource iconImage = new ImageResource(50,50);

    public SpecializationData() {}

}
