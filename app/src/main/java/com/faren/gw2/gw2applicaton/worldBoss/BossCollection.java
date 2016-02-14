package com.faren.gw2.gw2applicaton.worldBoss;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faren.gw2.gw2applicaton.R;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class BossCollection extends BaseAdapter {
    private LayoutInflater inflater;
    private List<GWWorldBoss> gwWorldBosses;
    private Activity activity;
    private String path;
    private ConnectivityManager connectivityManager;
    private NetworkInfo ni;

    public BossCollection(Activity activity, List<GWWorldBoss> gwItemInfoDisplayList) {
        inflater = LayoutInflater.from(activity);
        gwWorldBosses = gwItemInfoDisplayList;
        this.activity = activity;
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/boss/images/";


    }

    @Override
    public int getCount() {
        return gwWorldBosses.size();
    }

    @Override
    public GWWorldBoss getItem(int position) {
        return gwWorldBosses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_boss, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        final GWWorldBoss currentListData = getItem(position);
        String time = currentListData.time.replaceFirst(" UTC", "");
        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutesT = Integer.parseInt(time.split(":")[1]);
        int hourT = Integer.parseInt(time.split(":")[0]);

        int diffSeconds = -((hour) * 3600 + minutes * 60) + (
                (hourT + TimeZone.getDefault().getRawOffset() / 3600000) * 3600 + minutesT * 60);


        int diffHours = diffSeconds / 3600;
        int diffMinutes = (diffSeconds % 3600) / 60;
        mViewHolder.timeBoss.setText(String.format("%02d", diffHours) + ":" + String.format("%02d", diffMinutes));


        if (diffMinutes <= 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mViewHolder.bossLayout.setBackground(new ColorDrawable(Color.RED));
            } else {
                mViewHolder.bossLayout.setBackgroundDrawable(new ColorDrawable(Color.RED));
            }
            mViewHolder.timeBoss.setText("In progress ...");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mViewHolder.bossLayout.setBackground(new ColorDrawable(Color.WHITE));
            } else {
                mViewHolder.bossLayout.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            }
        }
        mViewHolder.nameBoss.setText(currentListData.nameBoss);
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Uri uri = Uri.parse(currentListData.url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
                return false;
            }

        });

        return convertView;
    }

    private class MyViewHolder {

        ImageView imageBoss;
        TextView nameBoss;
        TextView timeBoss;
        LinearLayout bossLayout;

        public MyViewHolder(View item) {
            imageBoss = (ImageView) item.findViewById(R.id.imageBoss);
            nameBoss = (TextView) item.findViewById(R.id.bossName);
            timeBoss = (TextView) item.findViewById(R.id.bossTimer);
            bossLayout = (LinearLayout) item.findViewById(R.id.bossLinearLayout);

        }
    }
}
