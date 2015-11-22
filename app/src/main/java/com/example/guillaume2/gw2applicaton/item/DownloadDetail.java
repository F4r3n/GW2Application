package com.example.guillaume2.gw2applicaton.item;

import android.os.AsyncTask;

import com.example.guillaume2.gw2applicaton.CallerBack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by guillaume2 on 17/11/15.
 */
public class DownloadDetail extends AsyncTask<Void, Void, Void> {

    private List<String> ids;
    private CallerBack callerBack;
    String url = "https://api.guildwars2.com/v2/items/";
    private String id = "";
    private int index = 0;
    public DownloadDetail(CallerBack callerBack, List<String> ids) {
        this.ids = ids;
        this.callerBack = callerBack;

    }
    @Override
    protected Void doInBackground(Void ... v) {
        for(int i = 0; i < ids.size(); ++i) {
            id = ids.get(i);
            index = i;
            retrieveData(ids.get(i));

        }
        return null;
    }

    private void retrieveData(String id) {
        URL url;
        StringBuilder response = new StringBuilder();

        HttpURLConnection urlConnection = null;
        try {
            url = new URL(this.url + id);

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
                e.printStackTrace();
            }
        }
        callerBack.notifyUpdate(this, response.toString(), index, id);

    }


}
