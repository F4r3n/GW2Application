package com.faren.gw2.gw2applicaton.dyeDisplay;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.faren.gw2.gw2applicaton.R;

import java.util.List;

/**
 * Created by guillaume2 on 06/02/16.
 */
public class DyeCollection extends BaseAdapter {
    LayoutInflater inflater;
    private String path;
    private Activity activity;
    private List<GWDyeDisplay> dyes;

    public DyeCollection(Activity activity, List<GWDyeDisplay> dyes) {
        inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.dyes = dyes;
    }

    @Override
    public int getCount() {
        return dyes.size();
    }

    @Override
    public GWDyeDisplay getItem(int position) {
        return dyes.get(position);
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

        final GWDyeDisplay currentListData = getItem(position);
        // System.out.println("type " +currentListData.item.gwItemData.detailObject);


        return convertView;
    }


    private class MyViewHolder {


        public MyViewHolder(View item) {

        }
    }
}


