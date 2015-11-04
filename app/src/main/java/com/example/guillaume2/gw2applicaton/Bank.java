package com.example.guillaume2.gw2applicaton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 04/11/15.
 */
public class Bank extends GWObject{




    public List<BankObject> objects = new ArrayList<>();

    public Bank() {
        super.url = "account/bank";
        super.cat = CATEGORIES.BANK;
    }

    public void readFile(String result) {
        try {
            JSONArray reader = new JSONArray(result);
            for (int i = 0; i < reader.length(); i++) {

                if (!reader.get(i).toString().equals("null"))
                    objects.add(new BankObject(reader.getJSONObject(i)));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
