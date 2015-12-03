package com.example.guillaume2.gw2applicaton.Builder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.guillaume2.gw2applicaton.CATEGORIES;
import com.example.guillaume2.gw2applicaton.CallerBack;
import com.example.guillaume2.gw2applicaton.DataImageToDl;
import com.example.guillaume2.gw2applicaton.DownloadImage;
import com.example.guillaume2.gw2applicaton.Professions;
import com.example.guillaume2.gw2applicaton.ReadFilesSpecialization;
import com.example.guillaume2.gw2applicaton.Tool.FileManagerTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guillaume2 on 24/11/15.
 */
public class SpecializationManager implements CallerBack {

    private HashMap<Professions, HashMap<String, Specialization>> specializations;
    private int index = 0;
    private int size = 54;
    private ConnectivityManager connectivityManager;
    private Activity activity;
    private float progress = 0;
    private int speFound = 0;
    private ProgressDialog progressDialog;
    private int sizeImages = 0;
    private int imagesFound = 0;
    private HashMap<String, Boolean> imagesChecked = new HashMap<>();
    private CallerBack parent;

    public SpecializationManager(CallerBack parent, Activity activity) {
        specializations = new HashMap<>();
        this.activity = activity;
        connectivityManager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        initProgressDialog();
        this.parent = parent;
    }

    public HashMap<Professions, HashMap<String, Specialization>> getSpe() {
        return specializations;
    }

    private void retrieveImage() {
        final ArrayList<DataImageToDl> images = new ArrayList<>();
        progress = 0.0f;
        imagesFound = 0;
        for (Map.Entry<Professions, HashMap<String, Specialization>> entry : specializations.entrySet()) {
            HashMap<String, Specialization> value = entry.getValue();
            for (Map.Entry<String, Specialization> specializationEntry : value.entrySet()) {
                Specialization specialization = specializationEntry.getValue();
                if (!specialization.backgroundExists()
                        && !imagesChecked.containsKey(specialization.specializationData.backgroundImage.iconPath)) {
                    images.add(new DataImageToDl(specialization.specializationData.backgroundImage,
                            specialization.specializationData.backgroundImage.iconPath, 0));
                    imagesChecked.put(specialization.specializationData.backgroundImage.iconPath, true);
                }
                if (!specialization.iconExists() && !imagesChecked.containsKey(specialization.specializationData.iconImage.iconPath)) {
                    images.add(new DataImageToDl(specialization.specializationData.iconImage,
                            specialization.specializationData.iconImage.iconPath, 0));
                    imagesChecked.put(specialization.specializationData.iconImage.iconPath, true);
                }
                for (Trait t : specialization.specializationData.majorTraits) {
                    if (!t.iconExists() && !imagesChecked.containsKey(t.iconImage.iconPath) && t.iconImage.iconUrl != null) {
                        images.add(new DataImageToDl(t.iconImage, t.iconImage.iconPath, 0));
                        imagesChecked.put(t.iconImage.iconPath, true);
                    }

                    for (TraitFact traitFact : t.traited_facts) {
                        if (!traitFact.iconExists() && !imagesChecked.containsKey(traitFact.iconImage.iconPath) && traitFact.iconImage.iconUrl != null) {
                            images.add(new DataImageToDl(traitFact.iconImage, traitFact.iconImage.iconPath, 0));
                            imagesChecked.put(t.iconImage.iconPath, true);
                        }
                    }
                    for (TraitFact traitFact : t.traits) {
                        if (!traitFact.iconExists() && !imagesChecked.containsKey(traitFact.iconImage.iconPath) && traitFact.iconImage.iconUrl != null)
                            images.add(new DataImageToDl(traitFact.iconImage, traitFact.iconImage.iconPath, 0));
                    }
                }

                for (Trait t : specialization.specializationData.minorTraits) {
                    if (!t.iconExists() && !imagesChecked.containsKey(t.iconImage.iconPath) && t.iconImage.iconUrl != null) {
                        images.add(new DataImageToDl(t.iconImage, t.iconImage.iconPath, 0));
                        imagesChecked.put(t.iconImage.iconPath, true);
                    }
                    for (TraitFact traitFact : t.traited_facts) {
                        if (!traitFact.iconExists() && !imagesChecked.containsKey(t.iconImage.iconPath) && t.iconImage.iconUrl != null) {
                            images.add(new DataImageToDl(traitFact.iconImage, traitFact.iconImage.iconPath, 0));
                            imagesChecked.put(t.iconImage.iconPath, true);
                        }
                    }
                    for (TraitFact traitFact : t.traits) {
                        if (!traitFact.iconExists() && !imagesChecked.containsKey(t.iconImage.iconPath) && traitFact.iconImage.iconUrl != null) {
                            images.add(new DataImageToDl(traitFact.iconImage, traitFact.iconImage.iconPath, 0));
                            imagesChecked.put(t.iconImage.iconPath, true);
                        }

                    }
                }

            }
        }
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        if (ni == null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, "No connection but " + images.size() + " missing", Toast.LENGTH_SHORT).show();

                }
            });
            parent.notifyUpdate(this, CATEGORIES.SPECIALIZATION);
            if (progressDialog.isShowing()) progressDialog.dismiss();
            return;
        }
        sizeImages = images.size();
        if (sizeImages == 0) {
            parent.notifyUpdate(this, CATEGORIES.SPECIALIZATION);
            if (progressDialog.isShowing()) progressDialog.dismiss();
        }
        new DownloadImage(this, images).execute();
    }

    public void readFiles() {
        progress = 0.0f;
        imagesFound = 0;
        speFound = 0;
        progressDialog.show();

        new ReadFilesSpecialization(this, specializations, size).execute();
    }

    public void initProgressDialog() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgress(0);

        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
    }

    public void request() {
        progress = 0.0f;
        progressDialog.setProgress((int) progress);
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
                specializations.put(profession, new HashMap<String, Specialization>());
            }
            Specialization specialization = new Specialization(profession, reader.getString("id"));
            specialization.specializationData.name = reader.getString("name");
            specialization.specializationData.elite = reader.getBoolean("elite");
            specialization.specializationData.iconImage.iconUrl = reader.getString("icon");
            specialization.specializationData.backgroundImage.iconUrl = reader.getString("background");
            JSONArray minor = reader.getJSONArray("minor_traits");
            for (int i = 0; i < minor.length(); i++) {
                specialization.specializationData.minorTraits.add(new Trait(minor.getInt(i)));
            }
            JSONArray major = reader.getJSONArray("major_traits");
            for (int i = 0; i < major.length(); i++) {
                specialization.specializationData.majorTraits.add(new Trait(major.getInt(i)));
            }
            specializations.get(profession).put(specialization.specializationData.name, specialization);

            System.out.println(profession);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestTraits() {
        for (Map.Entry<Professions, HashMap<String, Specialization>> entry : specializations.entrySet()) {
            HashMap<String, Specialization> value = entry.getValue();
            for (Map.Entry<String, Specialization> entrySpe : value.entrySet()) {
                entrySpe.getValue().requestTrait(this);
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
            System.out.println("images progress " + imagesFound);

            progress = (float) imagesFound / (float) sizeImages;
            if (o[1] != null)
                FileManagerTool.saveImage((Bitmap) o[1], (String) o[3]);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    progressDialog.setMessage("");
                    int p = (int) (progress * 100);
                    progressDialog.setProgress(p);
                }
            });
            if (progress == 1.0f) {
                progressDialog.dismiss();
                parent.notifyUpdate(this, CATEGORIES.SPECIALIZATION);
            }
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
                }
            }
            System.out.println("SpecializationManager " + progress);
        }
    }

    @Override
    public void cancel() {

    }


}
