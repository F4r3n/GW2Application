package com.faren.gw2.gw2applicaton.Builder;

import com.faren.gw2.gw2applicaton.ImageResource;

import java.util.ArrayList;
import java.util.List;


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
