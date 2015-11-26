package com.example.guillaume2.gw2applicaton.Builder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.guillaume2.gw2applicaton.CallerBack;
import com.example.guillaume2.gw2applicaton.DataImageToDl;
import com.example.guillaume2.gw2applicaton.DownloadImage;
import com.example.guillaume2.gw2applicaton.Professions;
import com.example.guillaume2.gw2applicaton.Tool.FileManagerTool;

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
    private int sizeImages = 0;
    private int imagesFound = 0;

    public SpecializationManager(Activity activity) {


        specializations = new HashMap<>();
        this.activity = activity;
        connectivityManager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        initProgressDialog();

    }

    private void retrieveImage() {
        ArrayList<DataImageToDl> images = new ArrayList<>();
        for (Map.Entry<Professions, List<Specialization>> entry : specializations.entrySet()) {
            List<Specialization> specializations = entry.getValue();
            for (Specialization specialization : specializations) {
                if (!specialization.backgroundExists())
                    images.add(new DataImageToDl(specialization.specializationData.backgroundUrl, specialization.specializationData.backgroundPath));
                if (!specialization.iconExists())
                    images.add(new DataImageToDl(specialization.specializationData.iconUrl, specialization.specializationData.iconPath));
                for (Trait t : specialization.specializationData.majorTraits) {
                    if (!t.iconExists())
                        images.add(new DataImageToDl(t.iconUrl, t.iconPath));
                    for (TraitFact traitFact : t.traited_facts) {
                        if (!traitFact.iconExists())
                            images.add(new DataImageToDl(traitFact.iconUrl, traitFact.iconPath));
                    }
                    for (TraitFact traitFact : t.traits) {
                        if (!traitFact.iconExists())
                            images.add(new DataImageToDl(traitFact.iconUrl, traitFact.iconPath));
                    }
                }
                for (Trait t : specialization.specializationData.minorTraits) {
                    if (!t.iconExists())
                        images.add(new DataImageToDl(t.iconUrl, t.iconPath));
                    for (TraitFact traitFact : t.traited_facts) {
                        if (!traitFact.iconExists())
                            images.add(new DataImageToDl(traitFact.iconUrl, traitFact.iconPath));
                    }
                    for (TraitFact traitFact : t.traits) {
                        if (!traitFact.iconExists())
                            images.add(new DataImageToDl(traitFact.iconUrl, traitFact.iconPath));
                    }
                }

            }
        }
        sizeImages = images.size();
        new DownloadImage(this, images).execute();
    }

    public void readFiles() {
        progressDialog.show();
        for (int i = 1; i <= size; ++i) {
            Specialization s = new Specialization(Integer.toString(i));
            s.readData(this);
            Professions profession = s.specializationData.profession;

            if (specializations.get(profession) == null) {
                specializations.put(profession, new ArrayList<Specialization>());
            }
            specializations.get(profession).add(s);
        }
        //retrieveImage();
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
        progressDialog.show();
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
            specialization.specializationData.name = reader.getString("name");
            specialization.specializationData.elite = reader.getBoolean("elite");
            specialization.specializationData.iconUrl = reader.getString("icon");
            specialization.specializationData.backgroundUrl = reader.getString("background");
            JSONArray minor = reader.getJSONArray("minor_traits");
            for (int i = 0; i < minor.length(); i++) {
                specialization.specializationData.minorTraits.add(new Trait(minor.getInt(i)));
            }
            JSONArray major = reader.getJSONArray("major_traits");
            for (int i = 0; i < major.length(); i++) {
                specialization.specializationData.majorTraits.add(new Trait(major.getInt(i)));
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

        if (o[0] instanceof DownloadImage) {
            imagesFound++;
            progress = (float) imagesFound / (float) sizeImages;
            FileManagerTool.saveImage((Bitmap) o[1], (String) o[3]);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    progressDialog.setMessage("");
                    int p = (int) (progress * 100);
                    progressDialog.setProgress(p);
                }
            });
            if (progress == 1.0f)
                progressDialog.dismiss();
        }

        if (o[0] instanceof Specialization) {
            speFound++;
            progress = (float) speFound / (float) size;
            final String message = (String) o[1];
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    progressDialog.setMessage(message);
                    int p = (int) (progress * 100);
                    progressDialog.setProgress(p);
                }
            });
            if (progress == 1.0f) {
                retrieveImage();
                if (sizeImages != 0) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.setMessage("Images");
                            progressDialog.setProgress(0);
                        }
                    });
                } else progressDialog.dismiss();

            }
            System.out.println("SpecializationManager " + progress);
        }
    }

    @Override
    public void cancel() {

    }


}
