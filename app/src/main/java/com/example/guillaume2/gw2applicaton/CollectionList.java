package com.example.guillaume2.gw2applicaton;

import android.app.ListActivity;
import android.os.Bundle;

/**
 * Created by guillaume2 on 17/11/15.
 */
public class CollectionList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bank);

        setListAdapter(new CollectionAdapter(this));
    }




}
