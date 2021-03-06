package com.faren.gw2.gw2applicaton;

import android.os.Environment;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Account extends GWObject {

    public AccountData accountData;

    public Account() {
        accountData = new AccountData();
        super.url = "account";
        super.cat = Categories.ACCOUNT;
        super.directoryFile = "/GW2App/Account";
        super.directoryName = "account.data";
    }

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
            accountData = gson.fromJson(br, AccountData.class);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeData() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + directoryFile);
        if(dir.mkdirs()){}
        File file = new File(dir, directoryName);

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            Gson gson = new Gson();
            String json = gson.toJson(accountData);
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
    protected void readFile(CallerBack parent, String results) {
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
