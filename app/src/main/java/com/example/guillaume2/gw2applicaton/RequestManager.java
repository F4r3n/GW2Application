package com.example.guillaume2.gw2applicaton;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 * Created by guillaume2 on 01/11/15.
 */
public class RequestManager extends Observable implements CallerBack {

    private List<Container> containers = new ArrayList<Container>();
    int index = 0;
    public Activity act;
    private boolean dialogBoxDisplay = false;
    private ProgressDialog progressDialog;
    private HashMap<CATEGORIES, Boolean> categoriesDownloaded = new HashMap<>();
    private GWObject object;

    public RequestManager(Activity act) {
        this.act = act;
        progressDialog = new ProgressDialog(act);
        progressDialog.setProgress(0);

        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        containers.add(new Container());

    }


    public void notifyChange(CATEGORIES cat) {
        setChanged();
        notifyObservers(cat);
    }

    public void overNotify() {
        clearChanged();
    }

    public void notifyFinish(CATEGORIES param) {
        categoriesDownloaded.put(param, true);
        switch (object.getCat()) {
            case ACCOUNT:
                containers.get(index).setAccount((Account) object);
                break;
            case DYES:
                break;
            case SKINS:
                break;
            case PVP:
                break;
            case BANK:
                containers.get(index).setBank((Bank) object);
                break;
        }
        notifyChange(object.getCat());

    }

    public Object getContainer(CATEGORIES cat) {
        switch (cat) {
            case ACCOUNT:
                return containers.get(index).getAccount();
            case DYES:
                break;
            case SKINS:
                break;
            case PVP:
                break;
            case BANK:
                return containers.get(index).getBank();

        }
        return null;
    }

    public boolean execute(GWObject cat, boolean displayProgressDialog) {
        object = cat;
        progressDialog.setProgress(0);
        this.dialogBoxDisplay = displayProgressDialog;
        if (categoriesDownloaded.containsKey(cat.getCat()) && categoriesDownloaded.get(cat.getCat())) {
            notifyChange(cat.getCat());
            Toast.makeText(act, "Already updated", Toast.LENGTH_SHORT).show();
            return categoriesDownloaded.get(cat.getCat());
        }
        if (dialogBoxDisplay) {
            progressDialog.setMessage("Start");
            progressDialog.show();
        }
        Request r = new Request(this);
        r.execute(cat);
        return false;
    }

    @Override
    public void notifyUpdate(Object... o) {

        final int progress = (int) ((float) o[1] * 100);

        if (o.length > 2) {
            final String s = (String) o[2];
            if (dialogBoxDisplay) {

                act.runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialog.setMessage(s);
                        progressDialog.setProgress(progress);
                    }
                });
            }


            if (dialogBoxDisplay) {

                if (progress == 100) {

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        }
        if (progress == 100) {
            object.writeData();
            notifyFinish(object.getCat());
        }

    }

    @Override
    public void cancel() {

    }
}
