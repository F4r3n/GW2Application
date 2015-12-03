package com.example.guillaume2.gw2applicaton;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by guillaume2 on 17/11/15.
 */
public class CollectionList extends AppCompatActivity implements CallerBack {
    private RequestManager rqm;
    private Bank bank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_bank);
        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            MyFragment list = new MyFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
        Collection c = (Collection) getApplication();
        bank = c.getContainer(0).getBank();
        rqm = c.getRequestManager();
        rqm.initProgressDialog(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        rqm.initProgressDialog(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update) {
            rqm.deleteFileData(bank);
            rqm.execute(new Bank(), true, this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }
    public static class MyFragment extends ListFragment implements CallerBack {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setListAdapter(new CollectionAdapter(getActivity()));

        }

        public void onListItemClick(ListView listView, View view, int position, long id) {

        }

        @Override
        public void notifyUpdate(Object... o) {
            if(o[0] instanceof CollectionList) {
                setListAdapter(new CollectionAdapter(getActivity()));
                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void cancel() {}
    }
}
