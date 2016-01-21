package com.faren.gw2.gw2applicaton;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.faren.gw2.gw2applicaton.item.GWItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by guillaume2 on 18/01/16.
 */
public class ItemsActivity extends AppCompatActivity implements CallerBack {

    private Button updateItems;
    private GW2ItemHelper db ;
    private SQLiteDatabase database;
    private int index = 0;
    private int numberThreads = 0;
    private int numberDl = 0;
    private final int THREAD_POOL_SIZE = 20;
    private String result = "";
    private SQLiteStatement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        updateItems = (Button) findViewById(R.id.updateItems);
        db = new GW2ItemHelper(this);

    }

    public void onUpdateButton(View view) {

        db.onUpgrade(database, 1, 2);
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
        if (o[0] instanceof RequestHttp && (int) o[2] == 0) {
            float progress;
            final String result = (String) o[1];
            System.out.println("Result " + result);
            int sizeToken = 0;
            JSONArray array;
            final ArrayList<String> idList = new ArrayList<>();
            String ids = "";
            try {
                array = new JSONArray(result);
                for (int i = 0; i < array.length(); ++i) {
                    String id = array.getString(i);
                    if (sizeToken == 0) {
                        ids = "";
                    }

                    progress = (float) i / array.length();
                    ids += id + ",";
                    sizeToken++;
                    if (sizeToken > 100) {
                        ids = ids.substring(0, ids.length() - 1);
                        sizeToken = 0;
                        idList.add(ids);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < idList.size(); ) {
                            if (numberThreads < THREAD_POOL_SIZE) {
                                RequestHttp requestHttp = new RequestHttp(ItemsActivity.this,
                                        "https://api.guildwars2.com/v2/items?ids=" + idList.get(i), 1);
                                if (Build.VERSION.SDK_INT >= 11)
                                    requestHttp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                else
                                    requestHttp.execute();
                                i++;
                                numberThreads++;
                                System.out.println("Number threads " + numberThreads);
                            }
                            if (numberThreads >= THREAD_POOL_SIZE) {
                                sleep(10000);
                                System.out.println("Sleep");
                            }

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();

        } else if (o[0] instanceof RequestHttp && (int) o[2] == 1) {
            numberThreads--;
            index++;
            System.out.println(index);
            insertValue((String) o[1]);
        }
    }

    private void insertValue(String result) {
        try {
            JSONArray array = new JSONArray(result);
            GWItem item = new GWItem();
            String val = "";
            String id = "";
            String name = "";
            String description = "";
            for (int i = 0; i < array.length(); ++i) {

                val = array.getString(i);
                id = array.getJSONObject(i).getString("id");
                name = array.getJSONObject(i).getString("name");
                description = array.getJSONObject(i).getString("description");
                System.out.println("id " + id);
                System.out.println("index " + numberDl);

              /* stmt.bindString(1, id);
                stmt.bindString(2, name);
                stmt.bindString(3, description);
                stmt.executeInsert();*/

                numberDl++;

            }
            val = null;
            id = null;
            name = null;
            description = null;
            item = null;
            array = null;
            System.gc();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        index = 0;
    }

    @Override
    public void cancel() {

    }
}
