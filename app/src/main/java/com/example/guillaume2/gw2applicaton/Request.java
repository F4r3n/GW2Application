package com.example.guillaume2.gw2applicaton;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by guillaume2 on 01/11/15.
 */
public class Request extends AsyncTask<String, Void, String> {
    private String key = "12C8EE8F-92B6-ED4C-9D8C-EEE086172362A540E7AE-2957-4F7A-BBD0-EAE8960DD48B";
    private String result;
    private String param;
    private HashMap<String, Container> containers;

    public Request(HashMap<String, Container> containers) {
       this.containers = containers;
    }

    public String getResult() {
        return result;
    }

    @Override
    protected String doInBackground(String... params) {
        param = params[1];
        return send(params[0]);
    }

    public String send(String m) {
        URL url;
        StringBuffer response = new StringBuffer();

        HttpURLConnection urlConnection = null;
        try {
            url = new URL("https://api.guildwars2.com/v2/" + m + "?access_token=12C8EE8F-92B6-ED4C-9D8C-EEE086172362A540E7AE-2957-4F7A-BBD0-EAE8960DD48B");

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }
        return response.toString();
    }

    protected void onPostExecute(String result) {
        switch (param) {
            case "Account":
                if(containers.get(param) != null)
                containers.put(param, new Account(result));
                break;
        }

        System.out.println(result);
    }


    protected void onPreExecute() {
    }
}
