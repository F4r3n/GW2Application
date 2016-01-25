package com.faren.gw2.gw2applicaton;

import android.app.FragmentManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.faren.gw2.gw2applicaton.item.GWItemInfoDisplay;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ItemsActivity extends AppCompatActivity implements CallerBack {

    private GW2ItemHelper db;
    private int index = 0;
    private int numberThreads = 0;
    private final int THREAD_POOL_SIZE = 20;
    private List<GWItemInfoDisplay> itemInfoDisplays = new ArrayList<>();
    private FragmentItems list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        db = new GW2ItemHelper(this);
        FragmentManager fm = getFragmentManager();


        if (fm.findFragmentById(R.id.listFragment) == null) {
            list = new FragmentItems();
            fm.beginTransaction().add(R.id.listFragment, list).commit();
        }
    }


    public void searchButton(View view) {
        EditText nameText = (EditText) findViewById(R.id.nameToSearch);
        String name = nameText.getText().toString();

        EditText vendorText = (EditText) findViewById(R.id.vendorValue);
        String vendorValue = vendorText.getText().toString();

        EditText levelText = (EditText) findViewById(R.id.level);
        String levelValue = levelText.getText().toString();

        if (!name.equals(""))
            itemInfoDisplays = db.selectItem(name, levelValue, vendorValue);

        list.updateData(this, itemInfoDisplays);
        list.setListShown(true);
    }

    public void onUpdateButton(View view) {

        //db.onUpgrade(database, 1, 2);
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        } else new RequestHttp(this, "https://api.guildwars2.com/v2/items", 0).execute();

    }

    @Override
    public void notifyUpdate(Object... o) {
        if (o[0] instanceof RequestHttp && (int) o[2] == 0) {
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
        }
    }


    @Override
    public void cancel() {

    }


}
