package com.faren.gw2.gw2applicaton;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.faren.gw2.gw2applicaton.Builder.BuilderActivity;
import com.faren.gw2.gw2applicaton.Builder.DialogSpeMean;
import com.faren.gw2.gw2applicaton.Builder.SpecializationManager;
import com.faren.gw2.gw2applicaton.Tool.FileManagerTool;
import com.faren.gw2.gw2applicaton.itemDisplay.ItemsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CallerBack, DialogSpeMean.DialogListener {


    private RequestManager requestManager;
    private SpecializationManager specializationManager;
    private LinearLayout linearLayoutAccount;
    private LinearLayout linearLayoutBuilder;
    private DialogSpeMean dialogSpeMean;
    private String nameFileKeyStorage = "key.dat";
    public String keyValue = "";
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/info/";

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

        String r = "";
        try {
            r = FileManagerTool.readFile(path, "permission.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (r != null) {
            String[] to = r.split(" ");
            for (String t : to) {
                enableButton(t);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestManager.initProgressDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //Todo disable all buttons first



                String r = data.getStringExtra("Result");
                String[] to = r.split(" ");
                for (String t : to) {
                    System.out.println(t);
                    enableButton(t);
                }
            }
        }
    }

    public void onItemButton(View view) {
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
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
            Intent intent = new Intent(this, KeyManagerActivity.class);
            startActivityForResult(intent, 0);
            //saveDialog();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void notifyUpdate(Object... o) {
        System.out.println("notify " + o[0]);
        if (o[0] instanceof MainActivity) {
            if (o[1] == Categories.BANK) {
                Intent intent = new Intent(this, CollectionList.class);
                startActivity(intent);

            }
            if (o[1] == Categories.ACCOUNT) {
                alertView(((Account) requestManager.getContainer(Categories.ACCOUNT)).accountData.name,
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
            String permissions = "Permissions\n";
            final ArrayList<String> builder = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject((String) o[1]);
                JSONArray jsonArray = jsonObject.getJSONArray("permissions");
                for (int i = 0; i < jsonArray.length(); ++i) {
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
                    for (String s : builder) {
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
        if (permission.equals("account")) {
            Button b = (Button) (findViewById(R.id.accountButton));
            b.setEnabled(true);
            b = (Button) (findViewById(R.id.bankButton));
            b.setEnabled(true);
        }
        if (permission.equals("wallet")) {
            Button b = (Button) (findViewById(R.id.walletButton));
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
            }
        }
    }
}
