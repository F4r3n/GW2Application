package com.faren.gw2.gw2applicaton;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class KeyChecker extends AsyncTask<String, Void, String> implements CallerBack {
    private CallerBack parent;

    public KeyChecker(CallerBack callerBack) {
        parent = callerBack;
    }

    @Override
    protected String doInBackground(String... params) {
        send(params[0]);
        return null;
    }

    public void send(String key) {
        URL url;
        StringBuffer response = new StringBuffer();
        System.out.println("https://api.guildwars2.com/v2/tokeninfo?access_token=" + key);
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("https://api.guildwars2.com/v2/tokeninfo?access_token=" + key);

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
        parent.notifyUpdate(this, response.toString());
    }

    @Override
    public void notifyUpdate(Object... o) {
    }

    @Override
    public void cancel() {
    }
}
