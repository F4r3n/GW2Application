package com.example.guillaume2.gw2applicaton;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 24/11/15.
 */
public class Specialization implements CallerBack {

    public String name;
    public Bitmap background;
    public Bitmap icon;
    public String backgroundUrl;
    public String iconUrl;
    public int id;
    public boolean elite = false;
    public List<Trait> majorTraits = new ArrayList<>();
    public List<Trait> minorTraits = new ArrayList<>();

    public Specialization() {

    }

    public void requestTrait() {

        new RequestTrait(this, majorTraits, minorTraits).execute();
    }

    @Override
    public void notifyUpdate(Object... o) {
        if(o[0] instanceof RequestTrait) {
            
        }
    }

    @Override
    public void cancel() {

    }
}
