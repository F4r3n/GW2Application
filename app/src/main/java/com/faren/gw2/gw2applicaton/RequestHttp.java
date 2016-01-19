package com.faren.gw2.gw2applicaton;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RequestHttp extends AsyncTask<String, Void, String> {
    private CallerBack parent;
    private String request;
    private int id;
    public RequestHttp(CallerBack callerBack, String request, int id) {
        this.request = request;
        this.id = id;
        parent = callerBack;
    }

    @Override
    protected String doInBackground(String... params) {
        send();
        return null;
    }

    public void send() {
        URL url;
        System.out.println("Request " + request);
        StringBuilder response = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(request);

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
                assert urlConnection != null;
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }
        parent.notifyUpdate(this, response.toString(), id);
    }


}
