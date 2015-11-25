package com.example.guillaume2.gw2applicaton;

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


    public Trait(int id) {
        traits = new ArrayList<>();
        traited_facts = new ArrayList<>();
        this.id = id;
    }
}
