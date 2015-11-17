package com.example.guillaume2.gw2applicaton;

import android.graphics.Bitmap;
import android.os.Environment;

import com.example.guillaume2.gw2applicaton.item.DownloadDetail;
import com.example.guillaume2.gw2applicaton.item.GWItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private List<String> itemsId = new ArrayList<>();

    public Bank() {
        super.directoryFile = "/GW2App/Bank";
        super.directoryName = "bank.data";
        super.url = "account/bank";
        super.cat = CATEGORIES.BANK;
    }

    public void writeData() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + directoryFile);
        dir.mkdirs();
        File file = new File(dir, directoryName);

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            Gson gson = new Gson();
            String json = gson.toJson(objects);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieveImages() {
        List<String> s = new ArrayList<>();
        for (BankObject b : objects) {
            s.add(b.item.icon);
        }
        new DownloadImage(this, s).execute();
    }

    public void readFile(CallerBack parent, String result) {
        this.parent = parent;
        itemsId.clear();
        itemsFound = 0;
        imagesFound = 0;

        try {
            JSONArray reader = new JSONArray(result);
            itemsToSearch = reader.length();
            for (int i = 0; i < reader.length(); i++) {
                if (!reader.get(i).toString().equals("null")) {
                    itemsId.add(reader.getJSONObject(i).getString("id"));
                    objects.add(new BankObject(this, reader.getJSONObject(i)));
                } else itemsToSearch--;

            }
            imagesToSearch = itemsToSearch;

            new DownloadDetail(this, itemsId).execute();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void notifyUpdate(Object... o) {
        float progress = 0.0f;

        String s = "";
        //System.out.println("Bank " + o[2]);
        if (o[0] != null) {
            if (o[0] instanceof DownloadImage) {
                objects.get((Integer) o[2]).setImage((Bitmap) o[1]);
                s = "Picture " + objects.get((Integer) o[2]).getName() + "\n";
                System.out.print(s);

                imagesFound++;
            } else if (o[0] instanceof DownloadDetail) {
                System.out.println("index " + (int) o[2]);
                objects.get((int) o[2]).item = new GWItem(this, itemsId.get((int) o[2]));
                try {
                    objects.get((int) o[2]).item.readFile((String) o[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //itemsFound++;

            } else {
                if (o[0] instanceof GWItem) {
                    s = objects.get(itemsFound).getName();
                    System.out.println("GWItem " + s);
                    System.out.println("Url " + objects.get(0).item.icon);
                    itemsFound++;

                    if (itemsFound == itemsToSearch && !isSearchingImages) {
                        isSearchingImages = true;
                        retrieveImages();
                    }
                }
            }
        }
        if (itemsToSearch != 0) {
            progress = ((float) itemsFound + (float) imagesFound) / ((float) itemsToSearch + (float) imagesToSearch);
        }
        //  System.out.println("s " + s);
        //  System.out.println("o " + o[0]);

        if (!(o[0] instanceof DownloadDetail))
            parent.notifyUpdate(this, progress, s);
    }

    @Override
    public void cancel() {

    }
}
