package com.example.guillaume2.gw2applicaton.Builder;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.guillaume2.gw2applicaton.R;

import java.util.List;

/**
 * Created by guillaume2 on 03/12/15.
 */
public class DialogSpeChoice extends DialogFragment implements
        AdapterView.OnItemClickListener {


    ListView mylist;
    List<Specialization> specializations;
    Activity activity;

    public DialogSpeChoice() {}

    public void init(Activity activity, List<Specialization> spes) {
        specializations = spes;
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_spe_choice, null, false);
        mylist = (ListView) view.findViewById(R.id.list);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mylist.setAdapter(new ChoiceSpeAdapter(activity, specializations));
        mylist.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
