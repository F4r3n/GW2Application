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


    public Bank() {
        super.directoryFile = "/GW2App/Bank";
        super.directoryName = "bank.data";
        super.url = "account/bank";
        super.cat = CATEGORIES.BANK;
        bankData = new BankData();
    }

    public BankData getBankData() {
        return bankData;
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isExists() {
        File file = new File(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + directoryFile), directoryName);
        return file.exists();

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
            e.printStackTrace();
        }
        for (BankObject o : bankData.objects) {
            o.item = new GWItem(null, o.getId());
            o.item.readData();
        }

    }

    public void retrieveImages() {
        imagesUrlToDl.clear();
        for (BankObject b : bankData.objects) {
            if (!(new File(b.item.gwItemData.imagePath).exists())) {
                imagesUrlToDl.add(b.item.gwItemData.iconUrl);
                System.out.println("!path " + b.item.gwItemData.imagePath);
                imagesToSearch = imagesUrlToDl.size();
            } else {
                System.out.println("path " + b.item.gwItemData.imagePath);

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
                    bankData.objects.add(new BankObject(reader.getJSONObject(i)));

                    if (!new File(bankData.objects.get(bankData.objects.size() - 1).pathGWItem).exists()) {
                        itemsId.add(reader.getJSONObject(i).getString("id"));
                    } else {
                        bankData.objects.get(bankData.objects.size() - 1).item = new GWItem(this,reader.getJSONObject(i).getString("id"));
                        bankData.objects.get(bankData.objects.size() - 1).item.readData();
                        itemsToSearch--;
                    }
                } else itemsToSearch--;

            }

            if(itemsToSearch == 0) {
                parent.notifyUpdate(this, 1.0f, "Over");
                return;
            }

            //imagesToSearch = itemsToSearch;


            new DownloadDetail(this, itemsId).execute();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void deleteFileData() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + directoryFile);
        File file = new File(dir, directoryName);
        file.delete();

    }


    @Override
    public void notifyUpdate(Object... o) {
        float progress = 0.0f;

        String s = "";
        if (o[0] != null) {
            if (o[0] instanceof DownloadImage) {
                s = "Picture " + bankData.objects.get((Integer) o[2]).getName() + "\n";
                System.out.println("Saving " + bankData.objects.get((Integer) o[2]).getName() + "...");
                bankData.objects.get((Integer) o[2]).item.saveImage((Bitmap) o[1], bankData.objects.get((Integer) o[2]).getId());
                System.out.println("Saving Done");


                if (imagesFound == imagesToSearch) isSearchingImages = false;

                imagesFound++;
            } else if (o[0] instanceof DownloadDetail) {
                System.out.println("index " + (int) o[2]);
                BankObject object = bankData.objects.get((int) o[2]);
                object.item = new GWItem(this, itemsId.get((int) o[2]));
                object.pathGWItem = object.item.gwItemData.dataPath;
                // object.pathGWItem = object.item.gwItemData.dataPath + object.getId() + ".json";


                try {
                    bankData.objects.get((int) o[2]).item.readFile((String) o[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                if (o[0] instanceof GWItem) {
                    s = bankData.objects.get(itemsFound).getName();
                    System.out.println("GWItem " + s);
                    if (!bankData.objects.get(itemsFound).item.dataExists())
                        bankData.objects.get(itemsFound).item.writeData();


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
