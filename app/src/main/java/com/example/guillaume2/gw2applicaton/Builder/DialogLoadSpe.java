package com.example.guillaume2.gw2applicaton.Builder;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.guillaume2.gw2applicaton.CallerBack;
import com.example.guillaume2.gw2applicaton.R;

import java.util.List;

/**
 * Created by guillaume2 on 14/12/15.
 */
public class DialogLoadSpe extends DialogFragment implements
        AdapterView.OnItemClickListener {
    private ListView mylist;
    private List<String> files;
    private BuilderActivity activity;
    private CallerBack parent;
    private String pathFolder;

    public interface DialogListener {
        void onChoiceLoadSpe(DialogFragment dialog, String name);
    }

    public DialogLoadSpe() {
    }

    public void init(BuilderActivity buidlderActivity, List<String> files, CallerBack cb) {
        this.files = files;
        System.out.println(files);
        this.activity = buidlderActivity;
        parent = cb;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_load_file, null, false);
        mylist = (ListView) view.findViewById(R.id.list);
        System.out.print("OncreateView " + mylist);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("On activityCreated " + mylist);
        mylist.setAdapter(new ChoiceFileLoadAdapter(activity, files));
        mylist.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.activity.fileToLoad(files.get(position));
        dismiss();
    }
}
