package com.example.guillaume2.gw2applicaton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaume2 on 24/11/15.
 */
public class SpecializationManager implements CallerBack {

    private HashMap<Professions, List<Specialization>> specializations;
    private int index = 0;
    private int size = 54;

    public SpecializationManager() {
        specializations = new HashMap<>();

    }

    public void request() {
        new RequestSpecialization(this, size).execute();
    }

    public void readReceived(String file) {
        try {
            JSONObject reader = new JSONObject(file);
            Professions profession = Professions.valueOf(reader.getString("profession").toUpperCase());
            if (specializations.get(profession) == null) {
                specializations.put(profession, new ArrayList<Specialization>());
            }
            Specialization specialization = new Specialization();
            specialization.name = reader.getString("name");
            specialization.id = reader.getInt("id");
            specialization.elite = reader.getBoolean("elite");
            specialization.iconUrl = reader.getString("icon");
            specialization.backgroundUrl = reader.getString("background");
            JSONArray minor = reader.getJSONArray("minor_traits");
            for (int i = 0; i < minor.length(); i++) {
                specialization.minorTraits.add(new Trait(minor.getInt(i)));
            }
            JSONArray major = reader.getJSONArray("major_traits");
            for (int i = 0; i < major.length(); i++) {
                specialization.majorTraits.add(new Trait(major.getInt(i)));
            }
            specializations.get(profession).add(specialization);

            System.out.println(profession);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestTraits() {
        for (Map.Entry<Professions, List<Specialization>> entry : specializations.entrySet()) {

            List<Specialization> value = entry.getValue();
            int i = 0;
            for(Specialization v: value) {
                System.out.println("Request traits spe " + i);
                v.requestTrait();
            }
            // ...
        }
    }


    @Override
    public void notifyUpdate(Object... o) {
        if (o[0] instanceof RequestSpecialization) {
            readReceived((String) o[1]);
            index++;
            System.out.println("Spe " + index);
            if(index == size) {
                requestTraits();
            }
        }
    }

    @Override
    public void cancel() {

    }


}
