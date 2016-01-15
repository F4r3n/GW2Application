package com.faren.gw2.gw2applicaton;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public final class Request extends AsyncTask<GWObject, Integer, String> {
    public String key = "";
    private String result;
    private Categories param;
    private RequestManager rqm;
    private GWObject object;

    public Request(RequestManager rqm) {
        this.rqm = rqm;
    }

    public String getResult() {
        return result;
    }

    @Override
    protected String doInBackground(GWObject... object) {
        this.object = object[0];
        this.param = this.object.getCat();
        String p = this.object.getUrl();
        return send(p);
    }

    public String send(String m) {
        URL url;
        StringBuffer response = new StringBuffer();

        HttpURLConnection urlConnection = null;
        try {
            url = new URL("https://api.guildwars2.com/v2/" + m + "?access_token=" + key);

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
        object.readFile(rqm, result);
    }


    protected void onPreExecute() {
    }
}