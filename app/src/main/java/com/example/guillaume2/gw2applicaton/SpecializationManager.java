package com.example.guillaume2.gw2applicaton;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
    private ConnectivityManager connectivityManager;
    private Activity activity;
    private float progress = 0;
    private int speFound = 0;
    private ProgressDialog progressDialog;

    public SpecializationManager(Activity activity) {
        specializations = new HashMap<>();
        this.activity = activity;
        connectivityManager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        initProgressDialog();

    }

    public void initProgressDialog() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgress(0);

        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
    }

    public void request() {
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(activity, "No connection", Toast.LENGTH_SHORT).show();
            return;
        }

        new RequestSpecialization(this, size).execute();
    }

    public void readReceived(String file) {
        try {
            JSONObject reader = new JSONObject(file);
            Professions profession = Professions.valueOf(reader.getString("profession").toUpperCase());
            if (specializations.get(profession) == null) {
                specializations.put(profession, new ArrayList<Specialization>());
            }
            Specialization specialization = new Specialization(profession, reader.getString("id"));
            specialization.name = reader.getString("name");
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
            for (Specialization v : value) {
                System.out.println("Request traits spe " + i);
                v.requestTrait(this);
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
            if (index == size) {
                requestTraits();
            }
        }

        if(o[0] instanceof Specialization) {
            speFound++;
            progress = (float)speFound/(float)size;
            final  String message = (String)o[1];
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    progressDialog.setMessage(message);
                    int p = (int) (progress*100);
                    progressDialog.setProgress(p);
                }
            });
            System.out.println("SpecializationManager " + progress);
        }
    }

    @Override
    public void cancel() {

    }


}
