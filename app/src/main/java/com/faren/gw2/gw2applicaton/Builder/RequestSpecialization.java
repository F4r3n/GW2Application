package com.faren.gw2.gw2applicaton.Builder;

import android.os.AsyncTask;

import com.faren.gw2.gw2applicaton.CallerBack;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestSpecialization extends AsyncTask<Integer, Void, Void> {

    private int size;
    private CallerBack parent;
    private int index = 0;


    public RequestSpecialization(CallerBack cb, int size) {
        this.size = size;
        this.parent = cb;
    }


    @Override
    protected Void doInBackground(Integer... integers) {

        send("specializations?ids=all");

        return null;
    }


    public void send(String m) {
        URL url;
        StringBuffer response = new StringBuffer();

        HttpURLConnection urlConnection = null;
        try {
            url = new URL("https://api.guildwars2.com/v2/" + m);

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
        try {
            JSONArray reader = new JSONArray(response.toString());

            for(int i = 0; i < reader.length(); ++i) {
                parent.notifyUpdate(this, reader.getString(i), i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    protected void onPreExecute() {

    }


}
