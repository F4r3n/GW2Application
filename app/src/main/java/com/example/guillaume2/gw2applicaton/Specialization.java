package com.example.guillaume2.gw2applicaton;

import android.graphics.Bitmap;
import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 24/11/15.
 */
public class Specialization implements CallerBack {

    public String name;
    public String path;
    transient public Bitmap background;
    transient public Bitmap icon;
    public String backgroundUrl;
    public String iconUrl;
    public String id;
    public Professions profession;
    public boolean elite = false;
    public List<Trait> majorTraits = new ArrayList<>();
    public List<Trait> minorTraits = new ArrayList<>();
    transient int index = 1;
    private transient CallerBack parent;

    public Specialization(Professions p, String id) {
        this.id = id;
        this.profession = p;
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/spe/data/" + id + ".json";

    }

    public void requestTrait(CallerBack cb) {
        parent = cb;
        new RequestTrait(this, majorTraits, minorTraits).execute();
    }

    @Override
    public void notifyUpdate(Object... o) {
        if(o[0] instanceof RequestTrait) {
            System.out.println("Spe " + index + " " +majorTraits.size() +" " + minorTraits.size() );
            if(index == (majorTraits.size() + minorTraits.size())) {
                System.out.println("Write Data");
                writeData();
            }
            index++;

        }
    }

    public void writeData() {
        File dir = new File(path.substring(0, path.lastIndexOf("/") + 1));
        dir.mkdirs();
        File file = new File(dir, id + ".json");

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            Gson gson = new Gson();
            String json = gson.toJson(this);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        parent.notifyUpdate(this, name);
    }

    @Override
    public void cancel() {

    }
}
