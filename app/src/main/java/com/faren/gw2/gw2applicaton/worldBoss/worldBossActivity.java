package com.faren.gw2.gw2applicaton.worldBoss;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.faren.gw2.gw2applicaton.CallerBack;
import com.faren.gw2.gw2applicaton.GW2ItemHelper;
import com.faren.gw2.gw2applicaton.R;

import java.util.List;

public class worldBossActivity extends AppCompatActivity implements CallerBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worldboss);
        GW2ItemHelper db = new GW2ItemHelper(this);
        FragmentManager fm = getFragmentManager();


        final FragmentBosses list;
        if (fm.findFragmentById(R.id.listFragment) == null) {
            list = new FragmentBosses();
            fm.beginTransaction().add(R.id.listFragment, list).commit();
        } else {
            list = new FragmentBosses();
            fm.beginTransaction().replace(R.id.listFragment, list).commit();
        }

        List<GWWorldBoss> worldBosses = db.selectBosses();
        list.updateData(this, worldBosses);

        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {

            @Override
            public void run() {
                list.notifyUpdateData();
                handler.postDelayed( this, 60 * 1000 );
            }
        }, 60 * 1000 );

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }
}
