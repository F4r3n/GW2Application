package com.faren.gw2.gw2applicaton.dyeDisplay;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.faren.gw2.gw2applicaton.CallerBack;
import com.faren.gw2.gw2applicaton.GW2ItemHelper;
import com.faren.gw2.gw2applicaton.R;


public class DyesActivity extends AppCompatActivity implements CallerBack {
    private GW2ItemHelper db;
    private FragmentDye list;


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
    }

    public void searchButton(View view) {

    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }
}
