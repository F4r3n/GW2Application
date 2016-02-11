package com.faren.gw2.gw2applicaton.itemDisplay;

import android.app.FragmentManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.faren.gw2.gw2applicaton.CallerBack;
import com.faren.gw2.gw2applicaton.GW2ItemHelper;
import com.faren.gw2.gw2applicaton.R;
import com.faren.gw2.gw2applicaton.RequestHttp;
import com.faren.gw2.gw2applicaton.item.GWItemInfoDisplay;
import com.faren.gw2.gw2applicaton.item.GWItem_Rarity;
import com.faren.gw2.gw2applicaton.item.GWItem_Type;

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
    private FrameLayout frameLayout;
    private TextView textViewInfoRequest;

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
        frameLayout = (FrameLayout) findViewById(R.id.listFragment);
        frameLayout.setVisibility(View.INVISIBLE);
        textViewInfoRequest = (TextView) findViewById(R.id.itemsFoundView);
    }


    public void searchButton(View view) {
        Log.v("Items ", "Searching");
        textViewInfoRequest.setText("Searching ...");
        EditText nameText = (EditText) findViewById(R.id.nameToSearch);
        String name = nameText.getText().toString();

        EditText levelTextMax = (EditText) findViewById(R.id.levelMax);
        String levelValueMax = levelTextMax.getText().toString();

        EditText levelTextMin = (EditText) findViewById(R.id.levelMin);
        String levelValueMin = levelTextMin.getText().toString();

        EditText vendorValueTextMinG = (EditText) findViewById(R.id.vendorGoldMin);
        String vendorValueMinG = vendorValueTextMinG.getText().toString();
        EditText vendorValueTextMinS = (EditText) findViewById(R.id.vendorSilverMin);
        String vendorValueMinS = vendorValueTextMinS.getText().toString();
        EditText vendorValueTextMinC = (EditText) findViewById(R.id.vendorCopperMin);
        String vendorValueMinC = vendorValueTextMinC.getText().toString();

        EditText vendorValueTextMaxG = (EditText) findViewById(R.id.vendorGoldMax);
        String vendorValueMaxG = vendorValueTextMaxG.getText().toString();
        EditText vendorValueTextMaxS = (EditText) findViewById(R.id.vendorSilverMax);
        String vendorValueMaxS = vendorValueTextMaxS.getText().toString();
        EditText vendorValueTextMaxC = (EditText) findViewById(R.id.vendorCopperMax);
        String vendorValueMaxC = vendorValueTextMaxC.getText().toString();

        int minValue = Integer.parseInt(vendorValueMinG) * 10000 + Integer.parseInt(vendorValueMinS) * 100 + Integer.parseInt(vendorValueMinC);
        int maxValue = Integer.parseInt(vendorValueMaxG) * 10000 + Integer.parseInt(vendorValueMaxS) * 100 + Integer.parseInt(vendorValueMaxC);

        if (minValue > maxValue) {
            int temp = minValue;
            minValue = maxValue;
            maxValue = temp;
        }

        List<String> rarityValues = new ArrayList<>();
        for (GWItem_Rarity rarity : GWItem_Rarity.values()) {
            String r = rarity.toString();
            CheckBox c = (CheckBox) findViewById(getResources().getIdentifier(r + "CheckBox", "id", getPackageName()));
            if (c.isChecked()) rarityValues.add(r);
        }

        List<String> typeValues = new ArrayList<>();
        for (GWItem_Type type : GWItem_Type.values()) {
            String r = type.getFormatedName();
            CheckBox c = (CheckBox) findViewById(getResources().getIdentifier(r + "CheckBox", "id", getPackageName()));
            if (c.isChecked()) typeValues.add(r);
        }
        String limit = "";
        if (name.equals("")) limit = "100";

        //if (!name.equals(""))
        itemInfoDisplays = db.selectItem(name, levelValueMin, levelValueMax,
                Integer.toString(minValue), Integer.toString(maxValue), rarityValues, typeValues, limit);

        frameLayout.setVisibility(View.VISIBLE);
        textViewInfoRequest.setText("Results found " + itemInfoDisplays.size());

        list.updateData(this, itemInfoDisplays);
        list.setListShown(true);
    }

    public void onCheckRarityButton(View view) {
        CheckBox checkBox = (CheckBox) view;
        boolean value = checkBox.isChecked();
        for (GWItem_Rarity rarity : GWItem_Rarity.values()) {
            String r = rarity.toString();
            CheckBox c = (CheckBox) findViewById(getResources().getIdentifier(r + "CheckBox", "id", getPackageName()));
            c.setChecked(value);
        }
    }

    public void onCheckTypeButton(View view) {
        CheckBox checkBox = (CheckBox) view;
        boolean value = checkBox.isChecked();
        for (GWItem_Type type : GWItem_Type.values()) {
            String r = type.getFormatedName();
            CheckBox c = (CheckBox) findViewById(getResources().getIdentifier(r + "CheckBox", "id", getPackageName()));
            c.setChecked(value);
        }
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
