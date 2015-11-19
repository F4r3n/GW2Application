package com.example.guillaume2.gw2applicaton;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by guillaume2 on 17/11/15.
 */
public class CollectionList extends ListActivity implements CallerBack {
    private RequestManager rqm;
    private Bank bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bank);
        Collection c = (Collection) getApplication();
        bank = c.getContainer(0).getBank();
        rqm = c.getRequestManager();
        rqm.act = this;
        setListAdapter(new CollectionAdapter(this));
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
            rqm.execute(new Bank(), false, this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void notifyUpdate(Object... o) {
        if(o[0] instanceof CollectionList) {
            setListAdapter(new CollectionAdapter(this));
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void cancel() {

    }
}
