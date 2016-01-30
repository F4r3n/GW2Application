package com.faren.gw2.gw2applicaton.Builder;

import android.os.Environment;

import com.faren.gw2.gw2applicaton.CallerBack;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class Specialization implements CallerBack {

    public SpecializationData specializationData;
    transient int index = 1;
    private transient CallerBack parent;

    public Specialization(Professions p, String id) {
        specializationData = new SpecializationData();
        this.specializationData.id = id;
        this.specializationData.profession = p;
        specializationData.path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/spe/data/" + id + ".json";
        specializationData.iconImage.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/GW2App/spe/image/" + id + "-icon.png";
        //specializationData.backgroundImage.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath()
          //      + "/GW2App/spe/image/" + id + "-background.png";
        specializationData.backgroundImage.iconPath = "background_spe_"+id;
    }

    public boolean iconExists() {
        return new File(specializationData.iconImage.iconPath).exists();
    }

    public boolean backgroundExists() {
        return new File(specializationData.backgroundImage.iconPath).exists();
    }

    public Specialization(String id) {
        specializationData = new SpecializationData();
        this.specializationData.id = id;
        specializationData.path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/spe/data/" + id + ".json";
        specializationData.iconImage.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/GW2App/spe/image/" + id + "-icon.png";
       // specializationData.backgroundImage.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath()
         //       + "/GW2App/spe/image/" + id + "-background.png";
        specializationData.backgroundImage.iconPath = "background_spe_"+id;
    }

    public void requestTrait(CallerBack cb) {
        parent = cb;
        new RequestTrait(this, specializationData.majorTraits, specializationData.minorTraits).execute();
    }

    public void fillTraits(HashMap<String, Trait> traitHashMap, CallerBack cb) {
        parent = cb;

        int i;
        for (i = 0; i < specializationData.majorTraits.size(); ++i) {
            specializationData.majorTraits.set(i,
                    traitHashMap.get(Integer.toString(specializationData.majorTraits.get(i).id)));
        }
        for (i = 0; i < specializationData.minorTraits.size(); ++i) {
            specializationData.minorTraits.set(i,
                    traitHashMap.get(Integer.toString(specializationData.minorTraits.get(i).id)));
        }

        System.out.println("Write Data");
        writeData();
    }


    @Override
    public void notifyUpdate(Object... o) {
        if (o[0] instanceof RequestTrait) {
            System.out.println("Spe " + index + " " + specializationData.majorTraits.size() + " " + specializationData.minorTraits.size());
            if (index == (specializationData.majorTraits.size() + specializationData.minorTraits.size())) {
                System.out.println("Write Data");
                writeData();
            }
            index++;
        }
    }

    public void writeData() {
        File dir = new File(specializationData.path.substring(0, specializationData.path.lastIndexOf("/") + 1));
        dir.mkdirs();
        File file = new File(dir, specializationData.id + ".json");

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            Gson gson = new Gson();
            String json = gson.toJson(specializationData);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        parent.notifyUpdate(this, specializationData.name);
    }

    public void readData(CallerBack parent) {

        Gson gson = new Gson();
        System.out.println(specializationData.path);
        File file = new File(specializationData.path);
        if (!file.exists()) {
            System.out.println("File does not exist");
            return;
        } else {
            System.out.println("File does exist");
        }

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            specializationData = gson.fromJson(br, SpecializationData.class);
            System.out.println(specializationData);
            br.close();
        } catch (IOException e) {
        }
        parent.notifyUpdate(this, specializationData.name);
    }

    @Override
    public void cancel() {
    }
}
