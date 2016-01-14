package com.example.guillaume2.gw2applicaton;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.guillaume2.gw2applicaton.Builder.BuilderActivity;
import com.example.guillaume2.gw2applicaton.Builder.DialogSpeMean;
import com.example.guillaume2.gw2applicaton.Builder.SpecializationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CallerBack, DialogSpeMean.DialogListener {

    private Button accountButton;
    private Button infoButton;
    private RequestManager requestManager;
    private SpecializationManager specializationManager;
    private LinearLayout linearLayoutAccount;
    private LinearLayout linearLayoutBuilder;
    private DialogSpeMean dialogSpeMean;
    private String nameFileKeyStorage = "key.dat";
    public String keyValue = "";

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
        specializationManager = new SpecializationManager(this, this);
        requestManager.initProgressDialog(this);

        linearLayoutAccount = (LinearLayout) findViewById(R.id.AccountLinear);
        linearLayoutBuilder = (LinearLayout) findViewById(R.id.linearBuildEditor);

        accountButton = (Button) findViewById(R.id.accountButton);
        infoButton = (Button) findViewById(R.id.infoButton);
        try {
            keyValue = readKey();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void saveKey(String key) {
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(nameFileKeyStorage, Context.MODE_PRIVATE);
            outputStream.write(key.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readKey() throws IOException {
        if (!new File(getFilesDir(), nameFileKeyStorage).exists())
            return "No key";
        FileInputStream fis = openFileInput(nameFileKeyStorage);


        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }


    private void saveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name of the save");

// Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveKey(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    public void getInfo(View view) {
        Collection c = (Collection) getApplication();
        c.key = keyValue;
        switch (view.getId()) {
            case R.id.infoButton:
                requestManager.execute(keyValue, new Account(), true, this);
                break;
            case R.id.bankButton:
                requestManager.execute(keyValue, new Bank(), true, this);
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

    private void alertView(String message, String title) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(title)
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
        } else if (id == R.id.action_add) {
            saveDialog();
        } else if (id == R.id.action_see_key) {
            try {
                keyValue = readKey();
                alertView(keyValue, "Key");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if( id == R.id.action_key_permissions) {
            System.out.println("Key value " + keyValue);
            ConnectivityManager connectivityManager;
            connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
            if (ni == null) {
                Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
                return true;
            }
            new KeyChecker(this).execute(keyValue);
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
                alertView(((Account) requestManager.getContainer(CATEGORIES.ACCOUNT)).accountData.name,
                        "Account info");
                requestManager.overNotify();
            }


        }
        if (o[0] instanceof SpecializationManager) {
            System.out.println("Over spe");
            Collection c = (Collection) getApplication();
            c.specializationManager = specializationManager;
            Intent intent = new Intent(this, BuilderActivity.class);
            startActivity(intent);
        }

        if (o[0] instanceof KeyChecker) {
            String permissions ="Permissions\n";
            final ArrayList<String> builder = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject((String) o[1]);
                JSONArray jsonArray = jsonObject.getJSONArray("permissions");
                for(int i = 0; i < jsonArray.length(); ++i) {
                    builder.add(jsonArray.getString(i));
                    permissions += "- " + jsonArray.getString(i) + "\n";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String finalPermissions = permissions;
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    alertView(finalPermissions, "Permissions");
                    for(String s : builder) {
                        enableButton(s);
                    }
                }
            });

        }
    }

    @Override
    public void cancel() {

    }

    public void enableButton(String permission) {
        if(permission.equals("account")) {
            Button b = (Button)(findViewById(R.id.accountButton));
            b.setEnabled(true);
            b = (Button)(findViewById(R.id.bankButton));
            b.setEnabled(true);
        }
        if(permission.equals("wallet")) {
            Button b = (Button)(findViewById(R.id.walletButton));
            b.setEnabled(true);
        }
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
