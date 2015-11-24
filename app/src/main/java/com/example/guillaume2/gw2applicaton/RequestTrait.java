package com.example.guillaume2.gw2applicaton;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 24/11/15.
 */
public class RequestTrait extends AsyncTask<Integer, Void, Void> {

    private CallerBack parent;
    private int index = 0;
    private List<Trait> minorTraits;
    private List<Trait> majorTraits;
    private List<Trait> unionTraits;



    public RequestTrait(CallerBack cb, List<Trait> majorTraits, List<Trait> minorTraits) {
        this.majorTraits = majorTraits;
        this.minorTraits = minorTraits;
        unionTraits = new ArrayList<>(majorTraits);
        unionTraits.addAll(minorTraits);
        this.parent = cb;
    }


    @Override
    protected Void doInBackground(Integer... integers) {
        int i = 0;
        for (Trait t : unionTraits) {
            index = i;
            send("traits/" + t.id);
            i++;
        }
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
        System.out.println("index " +index);
        readData();
    }

    public void readData() {
        parent.notifyUpdate(this, true, index);
    }


    protected void onPreExecute() {

    }


}