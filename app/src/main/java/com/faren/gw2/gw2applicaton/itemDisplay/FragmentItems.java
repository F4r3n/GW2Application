package com.faren.gw2.gw2applicaton.itemDisplay;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.faren.gw2.gw2applicaton.CallerBack;
import com.faren.gw2.gw2applicaton.item.GWItemInfoDisplay;

import java.util.List;


public class FragmentItems extends ListFragment implements CallerBack {

    private List<GWItemInfoDisplay> gwItemInfoDisplays;
    private Activity activity;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    public void updateData(Activity activity, List<GWItemInfoDisplay> items) {
        this.gwItemInfoDisplays = items;
        this.activity = activity;
        setListAdapter(new ItemCollection(activity, gwItemInfoDisplays));
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
