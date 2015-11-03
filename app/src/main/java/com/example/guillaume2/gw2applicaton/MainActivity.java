package com.example.guillaume2.gw2applicaton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private Button accountButton;
    private Button infoButton;
    private RequestManager requestManager;
    private LinearLayout linearLayoutAccount;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestManager = new RequestManager();
        requestManager.addObserver(this);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutAccount = (LinearLayout) findViewById(R.id.AccountLinear);
        accountButton = (Button) findViewById(R.id.accountButton);
        infoButton = (Button) findViewById(R.id.infoButton);
    }

    public void getInfo(View view) {
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.infoButton:
                requestManager.execute(CATEGORIES.ACCOUNT);
        }
    }

    public void changeVisibility(View view) {
        switch (view.getId()) {
            case R.id.accountButton:
                if (linearLayoutAccount.getVisibility() == View.VISIBLE)
                    linearLayoutAccount.setVisibility(View.GONE);
                else linearLayoutAccount.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void alertView(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("GW2Application")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void update(Observable observable, Object data) {
        System.out.println(data);
        if (observable instanceof RequestManager) {
            if (data == CATEGORIES.ACCOUNT) {
                alertView(((Account)requestManager.getContainer(CATEGORIES.ACCOUNT)).name);
                requestManager.overNotify();
            }
        }
    }
}
