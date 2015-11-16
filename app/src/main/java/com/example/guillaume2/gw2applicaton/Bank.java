package com.example.guillaume2.gw2applicaton;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 04/11/15.
 */
public class Bank extends GWObject implements CallerBack {

    public List<BankObject> objects = new ArrayList<>();

    private int itemsToSearch = 0;
    public int itemsFound = 0;
    private int imagesToSearch = 0;
    public int imagesFound = 0;
    private CallerBack parent;
    private boolean isSearchingImages = false;

    public Bank() {
        super.url = "account/bank";
        super.cat = CATEGORIES.BANK;
    }

    public void retrieveImages() {
        List<String> s = new ArrayList<>();
        for (BankObject b : objects) {
            s.add(b.getUrl());
        }
        new DownloadImage(this, s).execute();
    }

    public void readFile(CallerBack parent, String result) {
        this.parent = parent;
        try {
            JSONArray reader = new JSONArray(result);
            itemsToSearch = reader.length();
            for (int i = 0; i < reader.length(); i++) {

                if (!reader.get(i).toString().equals("null")) {
                    objects.add(new BankObject(this, reader.getJSONObject(i)));
                } else itemsToSearch--;

            }
            imagesToSearch = itemsToSearch;


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void notifyUpdate(Object... o) {
        float progress = 0.0f;

        String s = "";
        System.out.println("Bank " + o[2]);
        if (o[0] != null) {
            if (o[0] instanceof DownloadImage) {
                objects.get((Integer) o[2]).setImage((Bitmap) o[1]);
                s = "Picture " + objects.get((Integer) o[2]).getName() + "\n";
                System.out.print(s);

                imagesFound++;
            } else {
                s = (String) o[2];
                itemsFound++;

                if (itemsFound == itemsToSearch && isSearchingImages == false) {
                    isSearchingImages = true;
                    retrieveImages();
                }
            }
        }
        if (itemsToSearch != 0) {
            progress = ((float) itemsFound + (float) imagesFound) / ((float) itemsToSearch + (float) imagesToSearch);
        }

        parent.notifyUpdate(this, progress, s);


    }

    @Override
    public void cancel() {

    }
}
