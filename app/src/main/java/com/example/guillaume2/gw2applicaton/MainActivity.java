package com.example.guillaume2.gw2applicaton;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.guillaume2.gw2applicaton.Builder.DialogSpeMean;
import com.example.guillaume2.gw2applicaton.Builder.SpecializationManager;

public class MainActivity extends AppCompatActivity implements CallerBack, DialogSpeMean.DialogListener {

    private Button accountButton;
    private Button infoButton;
    private RequestManager requestManager;
    private SpecializationManager specializationManager;
    private LinearLayout linearLayoutAccount;
    private LinearLayout linearLayoutBuilder;
    private DialogSpeMean dialogSpeMean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        dialogSpeMean = new DialogSpeMean();
        Bundle args = new Bundle();
        args.putString("title", "Update");
        args.putString("message", "Do you want to update? It will take about 10 minutes");
        dialogSpeMean.setArguments(args);


        requestManager = new RequestManager(this);
        specializationManager = new SpecializationManager(this);
        requestManager.initProgressDialog(this);

        linearLayoutAccount = (LinearLayout) findViewById(R.id.AccountLinear);
        linearLayoutBuilder = (LinearLayout) findViewById(R.id.linearBuildEditor);

        accountButton = (Button) findViewById(R.id.accountButton);
        infoButton = (Button) findViewById(R.id.infoButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestManager.initProgressDialog(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        System.out.println(requestCode);
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }


    public void getInfo(View view) {

        switch (view.getId()) {
            case R.id.infoButton:
                requestManager.execute(new Account(), true, this);
                break;
            case R.id.bankButton:
                requestManager.execute(new Bank(), true, this);
                break;
            case R.id.editorButton:
                dialogSpeMean.show(getFragmentManager(), "ChoiceSpeMean");
                break;

        }

    }

    public void changeVisibility(View view) {
        switch (view.getId()) {
            case R.id.accountButton:
                if (linearLayoutAccount.getVisibility() == View.VISIBLE)
                    linearLayoutAccount.setVisibility(View.GONE);
                else linearLayoutAccount.setVisibility(View.VISIBLE);
                break;
            case R.id.builderButton:
                if (linearLayoutBuilder.getVisibility() == View.VISIBLE)
                    linearLayoutBuilder.setVisibility(View.GONE);
                else linearLayoutBuilder.setVisibility(View.VISIBLE);
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void notifyUpdate(Object... o) {
        System.out.println("notify " + o[0]);
        if (o[0] instanceof MainActivity) {
            if (o[1] == CATEGORIES.BANK) {

                Intent intent = new Intent(this, CollectionList.class);
                startActivity(intent);

            }
            if (o[1] == CATEGORIES.ACCOUNT) {
                alertView(((Account) requestManager.getContainer(CATEGORIES.ACCOUNT)).accountData.name);
                requestManager.overNotify();
            }
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void onChoiceSpeMean(DialogFragment dialog, DialogSpeMean.CHOICE_DL_SPECIALIZATION choice) {
        if (dialog instanceof DialogSpeMean) {
            dialog.dismiss();
            if (choice.equals(DialogSpeMean.CHOICE_DL_SPECIALIZATION.YES)) {
                specializationManager.request();
            }
            if (choice.equals(DialogSpeMean.CHOICE_DL_SPECIALIZATION.NO)) {
                specializationManager.readFiles();

            } else {

            }
        }
    }
}
