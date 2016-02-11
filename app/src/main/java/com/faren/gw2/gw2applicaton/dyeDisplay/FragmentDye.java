package com.faren.gw2.gw2applicaton.dyeDisplay;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.faren.gw2.gw2applicaton.CallerBack;

import java.util.List;


public class FragmentDye extends ListFragment implements CallerBack {

    private List<GWDyeDisplay> gwDyeDisplays;
    private Activity activity;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void updateData(Activity activity, List<GWDyeDisplay> items) {
        this.gwDyeDisplays = items;
        this.activity = activity;
        setListAdapter(new DyeCollection(activity, gwDyeDisplays));
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {
    }
}
