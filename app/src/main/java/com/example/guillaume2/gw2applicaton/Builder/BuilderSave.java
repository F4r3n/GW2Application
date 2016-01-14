package com.example.guillaume2.gw2applicaton.Builder;

/**
 * Created by guillaume2 on 14/12/15.
 */
public class BuilderSave {
    Professions professions;
    public int []spe = new int[3];
    public int []posTrait = new int[9];

    public BuilderSave() {
        for(int i = 0; i < spe.length; ++i) {
            spe[i] = -1;
        }
        for(int i = 0; i < posTrait.length; ++i) {
            posTrait[i] = -1;
        }
    }

}
