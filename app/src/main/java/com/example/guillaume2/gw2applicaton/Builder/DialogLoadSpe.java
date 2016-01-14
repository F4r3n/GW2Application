package com.example.guillaume2.gw2applicaton.Builder;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
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
    private View v;
    private ListAdapter adapter;


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
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        registerForContextMenu(mylist);
        v = view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ChoiceFileLoadAdapter(activity, files);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.activity.fileToLoad(files.get(position));
        dismiss();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.list) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_file_selected, menu);
        }
        MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onContextItemSelected(item);
                return true;
            }
        };

        for (int i = 0, n = menu.size(); i < n; i++)
            menu.getItem(i).setOnMenuItemClickListener(listener);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch(item.getItemId()) {
            case R.id.action_delete:
                System.out.println("delete " + files.get(index) + index);
                activity.deleteFileSpe(files.get(index));
                files.remove(index);
                mylist.invalidateViews();
                return true;
            case R.id.action_rename:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
