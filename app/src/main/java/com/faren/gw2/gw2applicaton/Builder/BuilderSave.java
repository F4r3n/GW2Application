package com.faren.gw2.gw2applicaton.Builder;


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
