package com.example.guillaume2.gw2applicaton.Builder;

import android.os.AsyncTask;

import com.example.guillaume2.gw2applicaton.CallerBack;

import java.util.HashMap;

/**
 * Created by guillaume2 on 30/11/15.
 */
public class ReadFilesSpecialization extends AsyncTask<Void, Void, Void> {

    CallerBack parent;
    HashMap<Professions, HashMap<String, Specialization>> specializations;
    int size;
    public ReadFilesSpecialization(CallerBack cb,
                                   HashMap<Professions, HashMap<String, Specialization>> specializations,
                                   int size) {
        parent = cb;
        this.specializations = specializations;
        this.size = size;
    }
    @Override
    protected Void doInBackground(Void... params) {
        for (int i = 1; i <= size; ++i) {
            Specialization s = new Specialization(Integer.toString(i));
            s.readData(parent);
            Professions profession = s.specializationData.profession;

            if (specializations.get(profession) == null) {
                specializations.put(profession, new HashMap<String, Specialization>());
            }
            specializations.get(profession).put(s.specializationData.name, s);
        }
        return null;
    }
}
