package com.faren.gw2.gw2applicaton.Builder;


public class BuilderSave {
    Professions professions;
    public String []spe = new String[3];
    public int []posTrait = new int[9];

    public BuilderSave() {
        for(int i = 0; i < spe.length; ++i) {
            spe[i] = "";
        }
        for(int i = 0; i < posTrait.length; ++i) {
            posTrait[i] = -1;
        }
    }

}
