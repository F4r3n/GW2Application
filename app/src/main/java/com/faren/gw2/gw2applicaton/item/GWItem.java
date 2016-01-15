package com.faren.gw2.gw2applicaton.item;

import android.graphics.Bitmap;
import android.os.Environment;

import com.faren.gw2.gw2applicaton.CallerBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class GWItem implements CallerBack {

    public GWItemData gwItemData;
    public static final int MAX_WIDTH = 100;
    public static final int MAX_HEIGHT = 100;

    public GWItem(String id) {
        gwItemData = new GWItemData(id);
        gwItemData.dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/item/data/" + id + ".json";

        gwItemData.id = id;
    }

    public void readFile(String result) throws JSONException {
        JSONObject reader = new JSONObject(result);
        gwItemData.name = reader.getString("name");
        gwItemData.type = GWItem_Type.valueOf(reader.getString("type").toUpperCase());
        gwItemData.level = reader.getInt("level");
        gwItemData.rarity = GWItem_Rarity.valueOf(reader.getString("rarity").toUpperCase());
        //System.out.println(rarity);
        gwItemData.vendor_value = reader.getInt("vendor_value");
        JSONArray game_typesJSON = reader.getJSONArray("game_types");
        for (int i = 0; i < game_typesJSON.length(); i++) {
            gwItemData.game_types.add(GWItem_Gametypes.valueOf(game_typesJSON.getString(i).toUpperCase()));
        }
        JSONArray flagsJSON = reader.getJSONArray("flags");
        for (int i = 0; i < flagsJSON.length(); i++) {
            gwItemData.flags.add(GWItem_flags.valueOf(flagsJSON.getString(i).toUpperCase()));
        }
        JSONArray restrictionsJSON = reader.getJSONArray("restrictions");
        for (int i = 0; i < restrictionsJSON.length(); i++) {
            gwItemData.restrictions.add(GWItem_Restrictions.valueOf(restrictionsJSON.getString(i).toUpperCase()));
        }
        gwItemData.chat_link = reader.getString("chat_link");
        gwItemData.imageResource.iconUrl = reader.getString("icon");
        System.out.println("id " + gwItemData.imageResource.iconUrl);
        switch (gwItemData.type) {
            case CONSUMABLE:
                gwItemData.gwItemConsumable = new GWItemConsumable(reader.getJSONObject("details"));
                break;
            case ARMOR:
                gwItemData.gwItemArmor = new GWItemArmor(reader.getJSONObject("details"));
                break;
        }
    }

    public boolean dataExists() {
        File file = new File(gwItemData.dataPath);
        return file.exists();
    }

    public boolean imageExists() {
        System.out.println(gwItemData.imageResource);
        File file = new File(gwItemData.imageResource.iconPath);
        return file.exists();
    }

    public void writeData() {
        File dir = new File(gwItemData.dataPath.substring(0, gwItemData.dataPath.lastIndexOf("/") + 1));
        dir.mkdirs();
        File file = new File(dir, gwItemData.id + ".json");

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            Gson gson = new Gson();
            String json = gson.toJson(gwItemData);
            outputStream.write(json.getBytes());
            outputStream.close();
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
        File dir = new File(gwItemData.imageResource.iconPath.substring(0, gwItemData.imageResource.iconPath.lastIndexOf("/") + 1));
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
