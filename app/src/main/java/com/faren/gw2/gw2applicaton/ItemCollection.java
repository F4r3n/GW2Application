package com.faren.gw2.gw2applicaton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.faren.gw2.gw2applicaton.Tool.FileManagerTool;
import com.faren.gw2.gw2applicaton.item.GWItemInfoDisplay;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ItemCollection extends BaseAdapter implements CallerBack {
    LayoutInflater inflater;
    private List<GWItemInfoDisplay> gwItemInfoDisplays;
    private String path;
    private Activity activity;
    ConnectivityManager connectivityManager;


    NetworkInfo ni;

    public ItemCollection(Activity activity, List<GWItemInfoDisplay> gwItemInfoDisplayList) {
        inflater = LayoutInflater.from(activity);
        gwItemInfoDisplays = gwItemInfoDisplayList;
        this.activity = activity;
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/items/images/";
    }

    @Override
    public int getCount() {
        return gwItemInfoDisplays.size();
    }

    @Override
    public GWItemInfoDisplay getItem(int position) {
        return gwItemInfoDisplays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        final GWItemInfoDisplay currentListData = getItem(position);
        // System.out.println("type " +currentListData.item.gwItemData.detailObject);

        mViewHolder.itemName.setText(currentListData.name);
        mViewHolder.itemType.setText(currentListData.type);
        mViewHolder.itemDesc.setText(currentListData.description);

        int valuePrice = Integer.parseInt(currentListData.vendor_value);
        int goldPrice = valuePrice / 10000;
        int silverPrice = (valuePrice % 10000) / 100;
        int copperPrice = valuePrice - goldPrice * 10000 - silverPrice * 100;
        //System.out.println("Item " + mViewHolder.goldValue + " " + goldPrice + " " + silverPrice + " " + copperPrice);

        mViewHolder.goldValue.setText(String.format("%d", goldPrice));
        mViewHolder.silverValue.setText(String.format("%d", silverPrice));
        mViewHolder.copperValue.setText(String.format("%d", copperPrice));

        valuePrice = Integer.parseInt(currentListData.trading_value);
        goldPrice = valuePrice / 10000;
        silverPrice = (valuePrice % 10000) / 100;
        copperPrice = valuePrice - goldPrice * 10000 - silverPrice * 100;

        mViewHolder.goldValueTrading.setText(String.format("%d", goldPrice));
        mViewHolder.silverValueTrading.setText(String.format("%d", silverPrice));
        mViewHolder.copperValueTrading.setText(String.format("%d", copperPrice));

        mViewHolder.levelText.setText(currentListData.level);

        connectivityManager = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        ni = connectivityManager.getActiveNetworkInfo();


        String name = currentListData.iconUrl.substring(currentListData.iconUrl.lastIndexOf("/") + 1);
        if (!new File(path + name).exists()) {
            if (ni != null) {
                List<DataImageToDl> imageToDls = new ArrayList<>();
                imageToDls.add(new DataImageToDl(new ImageResource(currentListData.name,
                        currentListData.iconUrl, path, 50, 50),
                        name, 0));
                new DownloadImage(this, imageToDls, 0).execute();
            } else {
                mViewHolder.itemIcon.setImageBitmap(null);
            }
        } else {
            Bitmap bm = BitmapFactory.decodeFile(path + name);
            mViewHolder.itemIcon.setImageBitmap(bm);
        }
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Uri uri = Uri.parse("https://wiki.guildwars2.com/wiki/" + currentListData.name.replace(" ", "_"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
                return false;
            }

            public void onClick(View v) {

            }
        });
        return convertView;
    }

    @Override
    public void notifyUpdate(Object... o) {
        System.out.println("update");
        if (o[0] instanceof DownloadImage && o[1] != null) {
            String name = (String) o[3];
            FileManagerTool.saveImage((Bitmap) o[1], path + name);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });

        }
    }

    @Override
    public void cancel() {

    }



    private class MyViewHolder {
        TextView itemName, itemDesc, itemType, goldValue, silverValue, copperValue,
                goldValueTrading, silverValueTrading, copperValueTrading, levelText;
        ImageView itemIcon;

        public MyViewHolder(View item) {
            itemType = (TextView) item.findViewById(R.id.itemSub);
            itemIcon = (ImageView) item.findViewById(R.id.itemIcon);
            itemName = (TextView) item.findViewById(R.id.itemName);
            itemDesc = (TextView) item.findViewById(R.id.itemDesc);
            levelText = (TextView) item.findViewById(R.id.level);

            goldValue = (TextView) item.findViewById(R.id.goldPrice);
            silverValue = (TextView) item.findViewById(R.id.silverPrice);
            copperValue = (TextView) item.findViewById(R.id.copperPrice);

            goldValueTrading = (TextView) item.findViewById(R.id.goldPriceTrading);
            silverValueTrading = (TextView) item.findViewById(R.id.silverPriceTrading);
            copperValueTrading = (TextView) item.findViewById(R.id.copperPriceTrading);
        }
    }
}
