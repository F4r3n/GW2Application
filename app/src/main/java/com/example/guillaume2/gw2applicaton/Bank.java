package com.example.guillaume2.gw2applicaton;

import android.graphics.Bitmap;
import android.os.Environment;

import com.example.guillaume2.gw2applicaton.item.DownloadDetail;
import com.example.guillaume2.gw2applicaton.item.GWItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 04/11/15.
 */
public class Bank extends GWObject implements CallerBack {

    private BankData bankData;
    private int itemsToSearch = 0;
    public int itemsFound = 0;
    private int imagesToSearch = 0;
    public int imagesFound = 0;
    private CallerBack parent;
    private boolean isSearchingImages = false;
    private List<String> itemsId = new ArrayList<>();
    private List<String> imagesUrlToDl = new ArrayList<>();
    private String pathImages = "";
    private String imageDir = "/images/";

    public Bank() {
        super.directoryFile = "/GW2App/Bank";
        super.directoryName = "bank.data";
        super.url = "account/bank";
        super.cat = CATEGORIES.BANK;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + directoryFile + imageDir);
        pathImages = dir.getAbsolutePath();
        bankData = new BankData();
    }

    public BankData getBankData() {
        return bankData;
    }

    public String saveImage(Bitmap bmp, String id, String path) {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + directoryFile + path);
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

    public void writeData() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + directoryFile);
        dir.mkdirs();
        File file = new File(dir, directoryName);

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            Gson gson = new Gson();
            String json = gson.toJson(bankData);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isExists() {
        File file = new File(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + directoryFile), directoryName);
        if (file.exists()) return true;

        return false;
    }

    @Override
    public void readData() {

        Gson gson = new Gson();
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + directoryFile);
        File file = new File(dir, directoryName);
        if (!file.exists()) {
            System.out.println("File does not exist");

            return;
        } else {
            System.out.println("File does exist");
        }


        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            bankData = gson.fromJson(br, BankData.class);
            br.close();
        } catch (IOException e) {
        }

    }

    public void retrieveImages() {
        imagesUrlToDl.clear();
        for (BankObject b : bankData.objects) {
            if (!(new File(b.item.imagePath).exists())) {
                imagesUrlToDl.add(b.item.iconUrl);
                System.out.println("!path " + b.item.imagePath);
                imagesToSearch = imagesUrlToDl.size();
            } else {
                System.out.println("path " + b.item.imagePath);

                imagesFound++;
            }
        }
        if (!imagesUrlToDl.isEmpty())
            new DownloadImage(this, imagesUrlToDl).execute();
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
                    bankData.objects.add(new BankObject(this, reader.getJSONObject(i)));
                } else itemsToSearch--;

            }
            //imagesToSearch = itemsToSearch;

            new DownloadDetail(this, itemsId).execute();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void notifyUpdate(Object... o) {
        float progress = 0.0f;

        String s = "";
        if (o[0] != null) {
            if (o[0] instanceof DownloadImage) {
                s = "Picture " + bankData.objects.get((Integer) o[2]).getName() + "\n";

                saveImage((Bitmap) o[1], bankData.objects.get((Integer) o[2]).getId(), imageDir);

                if (imagesFound == imagesToSearch) isSearchingImages = false;

                imagesFound++;
            } else if (o[0] instanceof DownloadDetail) {
                System.out.println("index " + (int) o[2]);
                bankData.objects.get((int) o[2]).item = new GWItem(this, itemsId.get((int) o[2]));

                try {
                    bankData.objects.get((int) o[2]).item.readFile((String) o[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //itemsFound++;

            } else {
                if (o[0] instanceof GWItem) {
                    s = bankData.objects.get(itemsFound).getName();
                    System.out.println("GWItem " + s);
                    System.out.println("Url " + bankData.objects.get(0).item.iconUrl);
                    bankData.objects.get(itemsFound).setImagePath(pathImages + "/" + bankData.objects.get(itemsFound).getId() + ".png");


                    itemsFound++;

                    if (itemsFound == itemsToSearch && !isSearchingImages) {
                        isSearchingImages = true;
                        retrieveImages();
                    }
                }
            }
        }
        imagesToSearch = itemsToSearch;

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
