package com.faren.gw2.gw2applicaton.worldBoss;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.faren.gw2.gw2applicaton.CallerBack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class FragmentBosses extends ListFragment implements CallerBack {

    private List<GWWorldBoss> gwWorldBosses;
    private Activity activity;
    private BossCollection bs;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void updateData(worldBossActivity activity, List<GWWorldBoss> items) {
        this.gwWorldBosses = items;
        this.activity = activity;
        changeData();
        bs = new BossCollection(activity, gwWorldBosses);
        setListAdapter(bs);
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {
    }

    private void changeData() {
        List<GWWorldBoss> itemsToRemove = new ArrayList<>();
        for (GWWorldBoss b : gwWorldBosses) {
            String time = b.time.replaceFirst(" UTC", "");

            Calendar c = Calendar.getInstance();
            int minutes = c.get(Calendar.MINUTE);
            final int hour = c.get(Calendar.HOUR_OF_DAY);
            int minutesT = Integer.parseInt(time.split(":")[1]);
            int hourT = Integer.parseInt(time.split(":")[0]);

            int diffSeconds = -((hour) * 3600 + minutes * 60) + (
                    (hourT + TimeZone.getDefault().getRawOffset() / 3600000) * 3600 + minutesT * 60);


            if (diffSeconds < -15*60)
                itemsToRemove.add(b);
        }
        gwWorldBosses.removeAll(itemsToRemove);
    }

    public void notifyUpdateData() {

        changeData();
        bs.notifyDataSetChanged();
    }
}
