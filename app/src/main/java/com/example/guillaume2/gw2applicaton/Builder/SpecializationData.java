package com.example.guillaume2.gw2applicaton.Builder;

import android.graphics.Bitmap;

import com.example.guillaume2.gw2applicaton.Professions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 25/11/15.
 */
public class SpecializationData {
    public String name;
    public String path;
    transient public Bitmap background;
    transient public Bitmap icon;
    public String backgroundUrl;
    public String iconUrl;
    public String backgroundPath;
    public String iconPath;

    public String id;
    public Professions profession;
    public boolean elite = false;
    public List<Trait> majorTraits = new ArrayList<>();
    public List<Trait> minorTraits = new ArrayList<>();

    public SpecializationData() {

    }
}
