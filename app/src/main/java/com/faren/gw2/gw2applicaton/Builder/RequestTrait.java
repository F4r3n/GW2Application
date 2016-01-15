package com.faren.gw2.gw2applicaton.Builder;

import android.os.AsyncTask;

import com.faren.gw2.gw2applicaton.CallerBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RequestTrait extends AsyncTask<Integer, Void, Void> {

    private CallerBack parent;
    private int index = 0;
    private Trait currentTrait;
    private List<Trait> unionTraits;


    public RequestTrait(CallerBack cb, List<Trait> majorTraits, List<Trait> minorTraits) {

        unionTraits = new ArrayList<>(majorTraits);
        unionTraits.addAll(minorTraits);
        this.parent = cb;
    }


    @Override
    protected Void doInBackground(Integer... integers) {
        int i = 0;
        for (Trait t : unionTraits) {
            index = i;
            currentTrait = t;
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
        System.out.println("index " + index);
        readData(response.toString());
    }

    public void readData(String data) {
        try {
            JSONObject reader = new JSONObject(data);
            currentTrait.name = reader.getString("name");
            currentTrait.tier = reader.getInt("tier");
            currentTrait.description = reader.getString("description");
            currentTrait.slot = Trait.SLOT.valueOf(reader.getString("slot").toUpperCase());
            currentTrait.specialization = reader.getInt("specialization");
            currentTrait.iconImage.iconUrl = reader.getString("icon");
            if (reader.has("facts")) {
                JSONArray facts = reader.getJSONArray("facts");
                for (int i = 0; i < facts.length(); ++i) {
                    TraitFact traitFact = new TraitFact(facts.getJSONObject(i));
                    currentTrait.traits.add(traitFact);
                }
            }
            if (reader.has("traited_facts")) {
                JSONArray facts = reader.getJSONArray("traited_facts");
                for (int i = 0; i < facts.length(); ++i) {
                    TraitFact traitFact = new TraitFact(facts.getJSONObject(i));
                    currentTrait.traited_facts.add(traitFact);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        parent.notifyUpdate(this, true, index);
    }


    protected void onPreExecute() {

    }


}