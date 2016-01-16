package com.faren.gw2.gw2applicaton;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.faren.gw2.gw2applicaton.Tool.FileManagerTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class KeyManagerActivity extends AppCompatActivity implements CallerBack {
    private String nameFileKeyStorage = "key.dat";
    private String keyValue;
    private TextView textKeyView;
    public MainActivity parent;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/info/";
    private String result="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_key_manager);
        textKeyView = (TextView) findViewById(R.id.keyTextView);

    }

    private void saveKey(String key) {
        File dir = new File(path);
        dir.mkdirs();
        File file = new File(dir, nameFileKeyStorage);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(key.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String readKey() throws IOException {
        File file = new File(path + nameFileKeyStorage);
        if (!file.exists()) {
            System.out.println("File does not exist");
            return null;
        } else {
            System.out.println("File does exist");
        }

        BufferedReader br;
        String line;
        String out = "";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                out += line;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public void getLastKey(View view) {

        try {
            keyValue = readKey();
            textKeyView.setText(keyValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onConnect(View view) {
        System.out.println(textKeyView.getText().toString());
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        saveKey(keyValue);
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
            return;
        }
        new KeyChecker(this).execute(keyValue);
    }

    @Override
    public void notifyUpdate(Object... o) {
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

            for (String s : builder) {
                result += s + " ";
            }
            Intent data = new Intent();
            data.putExtra("Result", result);
            FileManagerTool.saveFile(path, "permission.dat", result);
            setResult(Activity.RESULT_OK, data);
        }
    }


    @Override
    public void cancel() {

    }
}
