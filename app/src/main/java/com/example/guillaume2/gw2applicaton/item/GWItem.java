package com.example.guillaume2.gw2applicaton.item;

import android.graphics.Bitmap;
import android.os.Environment;

import com.example.guillaume2.gw2applicaton.CallerBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by guillaume2 on 10/11/15.
 */
public class GWItem implements CallerBack {

    public GWItemData gwItemData;
    private CallerBack callerBack;
    public static final int MAX_WIDTH = 100;
    public static final int MAX_HEIGHT = 100;


    public GWItem(CallerBack cb, String id) {
        gwItemData = new GWItemData();
        gwItemData.dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/item/data/"+id+".json";
        gwItemData.imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/item/images/"+id+".png";
        callerBack = cb;
        gwItemData.id = id;
        gwItemData.game_types = new ArrayList<>();
        gwItemData.flags = new ArrayList<>();
        gwItemData.restrictions = new ArrayList<>();


    }

    public void readFile(String result) throws JSONException {
        JSONObject reader = new JSONObject(result);
        gwItemData.name = reader.getString("name");
        gwItemData.type = GWITEM_TYPE.valueOf(reader.getString("type").toUpperCase());
        gwItemData.level = reader.getInt("level");
        gwItemData.rarity = GWITEM_RARITY.valueOf(reader.getString("rarity").toUpperCase());
        //System.out.println(rarity);
        gwItemData.vendor_value = reader.getInt("vendor_value");
        JSONArray game_typesJSON = reader.getJSONArray("game_types");
        for (int i = 0; i < game_typesJSON.length(); i++) {
            gwItemData.game_types.add(GWITEM_GAMETYPES.valueOf(game_typesJSON.getString(i).toUpperCase()));
        }
        JSONArray flagsJSON = reader.getJSONArray("flags");
        for (int i = 0; i < flagsJSON.length(); i++) {
            gwItemData.flags.add(GWITEM_FLAGS.valueOf(flagsJSON.getString(i).toUpperCase()));
        }
        JSONArray restrictionsJSON = reader.getJSONArray("restrictions");
        for (int i = 0; i < restrictionsJSON.length(); i++) {
            gwItemData.restrictions.add(GWITEM_RESTRICTIONS.valueOf(restrictionsJSON.getString(i).toUpperCase()));
        }
        gwItemData.chat_link = reader.getString("chat_link");
        gwItemData.iconUrl = reader.getString("icon");
        System.out.println("icon " + gwItemData.iconUrl);


        //new DownloadImage(this).execute(icon);
        callerBack.notifyUpdate(this, 0.0f, gwItemData.name);

    }

    public boolean dataExists() {
        File file = new File(gwItemData.dataPath);
        if (file.exists()) return true;
        return false;
    }

    public void writeData() {
        File dir = new File(gwItemData.dataPath.substring(0,gwItemData.dataPath.lastIndexOf("/")+1));
        dir.mkdirs();
        File file = new File(dir, gwItemData.id+".json");

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            Gson gson = new Gson();
            String json = gson.toJson(gwItemData);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readData() {

        Gson gson = new Gson();

        File file = new File(gwItemData.dataPath);
        if (!file.exists()) {
            System.out.println("File does not exist");

            return;
        } else {
            System.out.println("File does exist");
        }


        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            gwItemData = gson.fromJson(br, GWItemData.class);
            br.close();
        } catch (IOException e) {
        }

    }

    public String saveImage(Bitmap bmp, String id) {
        File dir = new File(gwItemData.imagePath.substring(0,gwItemData.imagePath.lastIndexOf("/")+1));
        dir.mkdirs();

        File file = new File(dir, id + ".png");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }


}
