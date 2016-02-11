package com.faren.gw2.gw2applicaton.dyeDisplay;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.faren.gw2.gw2applicaton.CallerBack;
import com.faren.gw2.gw2applicaton.GW2ItemHelper;
import com.faren.gw2.gw2applicaton.R;

import java.util.List;


public class DyesActivity extends AppCompatActivity implements CallerBack {
    private GW2ItemHelper db;
    private FragmentDye list;
    private FrameLayout frameLayout;
    private TextView textViewInfoRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyes);
        db = new GW2ItemHelper(this);
        FragmentManager fm = getFragmentManager();


        if (fm.findFragmentById(R.id.listFragment) == null) {
            list = new FragmentDye();
            fm.beginTransaction().add(R.id.listFragment, list).commit();
        }
        frameLayout = (FrameLayout) findViewById(R.id.listFragment);
        frameLayout.setVisibility(View.INVISIBLE);
        textViewInfoRequest = (TextView) findViewById(R.id.itemsFoundView);
    }

    public void searchButton(View view) {
        String name = ((EditText) findViewById(R.id.nameToSearch)).getText().toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewInfoRequest.setText("Searching ...");
            }
        });
        final List<GWDyeDisplay> gwDyeDisplays = db.selectDyes(name);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                frameLayout.setVisibility(View.VISIBLE);
                textViewInfoRequest.setText("Results found :" + gwDyeDisplays.size());
            }
        });
        list.updateData(this, gwDyeDisplays);
    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }
}
