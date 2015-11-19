package com.example.guillaume2.gw2applicaton;

import android.os.Environment;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by guillaume2 on 01/11/15.
 */
//{"id":"F51F143E-F2A7-E211-8756-78E7D1936222","name":"Farenheight.3869",
// "world":2105,"guilds":["CD67104F-0D19-4418-BCAE-5EE2B4BF4505","BBCD95EF-B266-E411-AEFB-AC162DC05865","0D0BBAD7-EBFA-E411-A3E6-AC162DC0E835","37A2CEE2-2E3C-E411-AEFB-AC162DC05865"],"created":"2013-04-18T06:30:00Z"}
public class Account extends GWObject {


    private CallerBack parent;
    public AccountData accountData;


    public Account() {
        accountData = new AccountData();
        super.url = "account";
        super.cat = CATEGORIES.ACCOUNT;
        super.directoryFile = "/GW2App/Account";
        super.directoryName = "account.data";
    }



    public void readData() {

        System.out.println(directoryFile);

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
            accountData = gson.fromJson(br, AccountData.class);
            br.close();
        } catch (IOException e) {
        }

    }

    public void writeData() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + directoryFile);
        dir.mkdirs();
        File file = new File(dir, directoryName);

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            Gson gson = new Gson();
            String json = gson.toJson(accountData);
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
    protected void readFile(CallerBack parent, String results) {
        this.parent = parent;
        accountData.guilds.clear();
        parent.notifyUpdate(this, 0.0f, "Info");
        try {
            JSONObject reader = new JSONObject(results);
            accountData.id = reader.getString("id");
            accountData.name = reader.getString("name");
            accountData.world = reader.getString("world");
            accountData.dateCreation = reader.getString("created");

            JSONArray jsonArray = reader.optJSONArray("guilds");


            for (int i = 0; i < jsonArray.length(); i++) {

                accountData.guilds.add(jsonArray.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        parent.notifyUpdate(this, 1.0f, "Info end");
    }

    @Override
    protected void deleteFileData() {

    }


    @Override
    public String toString() {
        String s = "id " + accountData.id + "\n";
        s += "name " + accountData.name + "\n";
        s += "world " + accountData.world + "\n";
        for (String g : accountData.guilds)
            s += "Guild " + g + "\n";
        return s;
    }


    @Override
    public void cancel() {

    }
}
