package com.example.guillaume2.gw2applicaton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 04/11/15.
 */
public class BankObject {
    private String id;
    private int count;
    private String idSkin;

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

    private List<String> upgrades;
    private List<String> infusions;

    public BankObject(JSONObject json) {
        
        upgrades = new ArrayList<>();
        infusions = new ArrayList<>();
        try {
            id = json.getString("id");
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
