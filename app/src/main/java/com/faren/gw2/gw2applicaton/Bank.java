package com.faren.gw2.gw2applicaton;

import android.graphics.Bitmap;
import android.os.Environment;

import com.faren.gw2.gw2applicaton.item.DownloadDetail;
import com.faren.gw2.gw2applicaton.item.GWItem;
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

public class Bank extends GWObject implements CallerBack {

    private BankData bankData;
    private int itemsToSearch = 0;
    public int itemsFound = 0;
    private int imagesToSearch = 0;
    public int imagesFound = 0;
    private CallerBack parent;
    private boolean isSearchingImages = false;
    private List<String> itemsId = new ArrayList<>();
    private List<DataImageToDl> imagesUrlToDl = new ArrayList<>();
    private List<Integer> indexSent = new ArrayList<>();


    public Bank() {
        super.directoryFile = "/GW2App/Bank";
        super.directoryName = "bank.data";
        super.url = "account/bank";
        super.cat = Categories.BANK;
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
            o.item = new GWItem(o.getId());
            o.item.readData();
        }
    }

    public void retrieveImages() {
        int i = 0;
        for (BankObject o : bankData.objects) {
            System.out.println(o.item);
            if (!o.item.imageExists())
                imagesUrlToDl.add(new DataImageToDl(o.item.gwItemData.imageResource, o.getId(), i));
            i++;
        }

        if (!imagesUrlToDl.isEmpty())
            new DownloadImage(this, imagesUrlToDl, 0).execute();
    }

    public void readFile(CallerBack parent, String result) {
        this.parent = parent;
        boolean hasToDownloadImages = false;
        itemsId.clear();
        itemsFound = 0;
        imagesFound = 0;
        imagesToSearch = 0;
        itemsToSearch = 0;
        imagesUrlToDl.clear();
        indexSent.clear();

        int itemMissing = 0;

        try {
            JSONArray reader = new JSONArray(result);
            itemsToSearch = reader.length();
            for (int i = 0; i < reader.length(); i++) {
                if (!reader.get(i).toString().equals("null")) {
                    bankData.objects.add(new BankObject(reader.getJSONObject(i)));
                    if (!new File(bankData.objects.get(bankData.objects.size() - 1).pathGWItem).exists()) {
                        itemsId.add(reader.getJSONObject(i).getString("id"));
                        indexSent.add(i - itemMissing);
                    } else {
                        BankObject object = bankData.objects.get(bankData.objects.size() - 1);
                        object.item = new GWItem(reader.getJSONObject(i).getString("id"));
                        object.item.readData();
                        itemsToSearch--;
                        if (!object.item.imageExists()) {
                            hasToDownloadImages = true;
                            imagesToSearch++;
                        }
                    }
                } else {
                    itemsToSearch--;
                    itemMissing++;
                }
            }
            System.out.println("items " + itemsToSearch + "images" + imagesUrlToDl.size());
            System.out.println("Objects " + bankData.objects.size() + " " + indexSent.size());
            if (itemsToSearch == 0 && !hasToDownloadImages) {
                parent.notifyUpdate(this, 1.0f, "Over");
                return;
            }

            new DownloadDetail(this, itemsId).execute();

            if (itemsToSearch == 0 && hasToDownloadImages) {
                isSearchingImages = true;
                retrieveImages();
            }


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
                BankObject bankObject = bankData.objects.get((int)o[2]);
                s = "Picture " + bankObject.getName() + "\n";
                System.out.println("Saving " + bankObject.getName() + "...");
                bankObject.item.saveImage((Bitmap) o[1], bankObject.getId());
                System.out.println("Saving Done");
                imagesFound++;
                System.out.println(imagesFound + " " + itemsFound + " " + imagesToSearch + " " + itemsToSearch);
                if (imagesFound == imagesToSearch) isSearchingImages = false;


            } else if (o[0] instanceof DownloadDetail) {
                System.out.println("index " + o[2] + " " + indexSent.get((int) o[2]));

                BankObject object = bankData.objects.get(indexSent.get((int) o[2]));
                object.item = new GWItem((String) o[3]);
                object.pathGWItem = object.item.gwItemData.dataPath;
                if (!object.item.imageExists()) {
                    imagesToSearch++;
                }

                try {
                    object.item.readFile((String) o[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("GWItem number " + indexSent.get((int) o[2]));
                s = bankData.objects.get(indexSent.get((int) o[2])).getName();
                System.out.println("GWItem " + s);
                if (!bankData.objects.get(indexSent.get((int) o[2])).item.dataExists())
                    bankData.objects.get(indexSent.get((int) o[2])).item.writeData();


                itemsFound++;

                if (itemsFound == itemsToSearch && !isSearchingImages && imagesToSearch != 0) {
                    isSearchingImages = true;
                    retrieveImages();
                }
            }
        }


        if (itemsToSearch != 0 || imagesToSearch != 0) {
            progress = ((float) itemsFound + (float) imagesFound) / ((float) itemsToSearch + (float) imagesToSearch);
        }
        parent.notifyUpdate(this, progress, s);
    }

    @Override
    public void cancel() {
    }
}
