package com.example.guillaume2.gw2applicaton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 01/11/15.
 */
//{"id":"F51F143E-F2A7-E211-8756-78E7D1936222","name":"Farenheight.3869",
// "world":2105,"guilds":["CD67104F-0D19-4418-BCAE-5EE2B4BF4505","BBCD95EF-B266-E411-AEFB-AC162DC05865","0D0BBAD7-EBFA-E411-A3E6-AC162DC0E835","37A2CEE2-2E3C-E411-AEFB-AC162DC05865"],"created":"2013-04-18T06:30:00Z"}
public class Account  implements Container{
    private String result;
    private String name;
    private String id;
    private String world;
    private String dateCreation;
    private List<String> guilds = new ArrayList<String>();

    public Account(String result) {
        this.result = result;

        try {
            JSONObject reader = new JSONObject(result);
            id = reader.getString("id");
            name = reader.getString("name");
            world = reader.getString("world");
            dateCreation = reader.getString("created");

            JSONArray jsonArray = reader.optJSONArray("guilds");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++) {
                System.out.println(jsonArray.getString(i));
                guilds.add(jsonArray.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Name "+name);
    }


}
