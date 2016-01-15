package com.faren.gw2.gw2applicaton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.faren.gw2.gw2applicaton.item.GWItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BankObject {
    private String id;
    private int count;
    private String idSkin;
    transient public GWItem item;
    public String pathGWItem;
    private List<String> upgrades;
    private List<String> infusions;

    public String getPathGWItem() { return pathGWItem;}

    public List<String> getInfusions() {
        return infusions;
    }

    public List<String> getUpgrades() {
        return upgrades;
    }

    public String getIdSkin() {
        return idSkin;
    }

    public int getCount() {
        return count;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return item.gwItemData.description;
    }

    public Bitmap getIcon() {
        return BitmapFactory.decodeFile(item.gwItemData.imageResource.iconPath);
    }


    public GWItem getItem() {
        return item;
    }

    public void setItem(GWItem item) {

        this.item = item;
    }

    public String getName() {
        return item.gwItemData.name;
    }

    public BankObject(JSONObject json) {

        upgrades = new ArrayList<>();
        infusions = new ArrayList<>();
        try {
            id = json.getString("id");

            pathGWItem = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/item/data/"+id+".json";

            count = json.getInt("count");
            if (!json.isNull("skin"))
                idSkin = json.getString("skin");
            JSONArray up;
            if (!json.isNull("upgrades")) {
                up = json.getJSONArray("upgrades");
                for (int i = 0; i < up.length(); ++i) {
                    upgrades.add(up.getString(i));
                }
            }
            if (!json.isNull("infusions")) {
                up = json.getJSONArray("infusions");
                for (int i = 0; i < up.length(); ++i) {
                    infusions.add(up.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
