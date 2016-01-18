package com.faren.gw2.gw2applicaton;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by guillaume2 on 18/01/16.
 */
public class ItemsActivity extends AppCompatActivity implements CallerBack {

    private Button updateItems;
    GW2ItemHelper db = new GW2ItemHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        updateItems = (Button) findViewById(R.id.updateItems);
    }

    public void onUpdateButton(View view) {
        db.onUpgrade(db.getWritableDatabase(), 1, 2);
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
            return;
        } else new RequestHttp(this, "https://api.guildwars2.com/v2/items", 0).execute();

    }

    @Override
    public void notifyUpdate(Object... o) {
        if (o[0] instanceof RequestHttp) {
            float progress = 0.0f;
            final String result = (String) o[1];
            final ArrayList<String> strings = new ArrayList<>();
            System.out.println("Result " + result);

            JSONArray array = null;
            try {
                array = new JSONArray(result);
                for (int i = 0; i < array.length(); ++i) {
                    db.createGWItem(array.getString(i));
                    progress = (float)i/array.length();
                    System.out.println(progress);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void cancel() {

    }
}
