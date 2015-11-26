package com.example.guillaume2.gw2applicaton.Builder;

import android.os.Environment;

import com.example.guillaume2.gw2applicaton.CallerBack;
import com.example.guillaume2.gw2applicaton.Professions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by guillaume2 on 24/11/15.
 */
public class Specialization implements CallerBack {

    public SpecializationData specializationData;
    transient int index = 1;
    private transient CallerBack parent;

    public Specialization(Professions p, String id) {
        specializationData = new SpecializationData();
        this.specializationData.id = id;
        this.specializationData.profession = p;
        specializationData.path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/spe/data/" + id + ".json";
        specializationData.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/GW2App/spe/image/" + id + "-icon.png";
        specializationData.backgroundPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/GW2App/spe/image/" + id + "-background.png";

    }

    public boolean iconExists() {
        return new File(specializationData.iconPath).exists();
    }

    public boolean backgroundExists() {
        return new File(specializationData.backgroundPath).exists();
    }

    public Specialization(String id) {
        specializationData = new SpecializationData();
        this.specializationData.id = id;
        specializationData.path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/spe/data/" + id + ".json";
        specializationData.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/GW2App/spe/image/" + id + "-icon.png";
        specializationData.backgroundPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/GW2App/spe/image/" + id + "-background.png";
    }

    public void requestTrait(CallerBack cb) {
        parent = cb;
        new RequestTrait(this, specializationData.majorTraits, specializationData.minorTraits).execute();
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
        parent.notifyUpdate(this,specializationData.name);
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
