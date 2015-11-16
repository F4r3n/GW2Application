package com.example.guillaume2.gw2applicaton.item;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.guillaume2.gw2applicaton.CallerBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 10/11/15.
 */
public class GWItem implements CallerBack {
    String id;
    String url = "https://api.guildwars2.com/v2/items/";
    String chat_link;
    public String name;
    public String icon;
    String description;
    GWITEM_TYPE type;
    GWITEM_RARITY rarity;
    List<GWITEM_FLAGS> flags;
    List<GWITEM_GAMETYPES> game_types;
    List<GWITEM_RESTRICTIONS> restrictions;
    int level;
    int vendor_value;
    String default_skin;
    GWItemDetailObject object;
    private CallerBack callerBack;
    public Bitmap image;
    public static final int MAX_WIDTH = 20;
    public static final int MAX_HEIGHT = 20;


    public GWItem(CallerBack cb, String id) {
        callerBack = cb;
        this.id = id;
        game_types = new ArrayList<>();
        flags = new ArrayList<>();
        restrictions = new ArrayList<>();
        //System.out.println("Item " + this.id);
        new DownloadDetail().execute(id);
    }

    public void readFile(String result) throws JSONException {
        JSONObject reader = new JSONObject(result);
        name = reader.getString("name");
        type = GWITEM_TYPE.valueOf(reader.getString("type").toUpperCase());
        level = reader.getInt("level");
        rarity = GWITEM_RARITY.valueOf(reader.getString("rarity").toUpperCase());
        //System.out.println(rarity);
        vendor_value = reader.getInt("vendor_value");
        JSONArray game_typesJSON = reader.getJSONArray("game_types");
        for (int i = 0; i < game_typesJSON.length(); i++) {
            game_types.add(GWITEM_GAMETYPES.valueOf(game_typesJSON.getString(i).toUpperCase()));
        }
        JSONArray flagsJSON = reader.getJSONArray("flags");
        for (int i = 0; i < flagsJSON.length(); i++) {
            flags.add(GWITEM_FLAGS.valueOf(flagsJSON.getString(i).toUpperCase()));
        }
        JSONArray restrictionsJSON = reader.getJSONArray("restrictions");
        for (int i = 0; i < restrictionsJSON.length(); i++) {
            restrictions.add(GWITEM_RESTRICTIONS.valueOf(restrictionsJSON.getString(i).toUpperCase()));
        }
        chat_link = reader.getString("chat_link");
        icon = reader.getString("icon");
        //new DownloadImage(this).execute(icon);
        callerBack.notifyUpdate(this, 0.0f, name);

    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }


    public class DownloadDetail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            URL url;
            StringBuilder response = new StringBuilder();

            HttpURLConnection urlConnection = null;
            try {
                url = new URL(GWItem.this.url + params[0]);

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
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //  System.out.println("Item " + GWItem.this.id);
            //System.out.println("Item " + result);
            try {
                GWItem.this.readFile(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }




}
