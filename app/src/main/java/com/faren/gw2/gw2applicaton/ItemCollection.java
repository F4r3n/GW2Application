package com.faren.gw2.gw2applicaton;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.faren.gw2.gw2applicaton.item.GWItemInfoDisplay;

import java.util.List;


public class ItemCollection extends BaseAdapter{
    LayoutInflater inflater;
    private List<GWItemInfoDisplay> gwItemInfoDisplays;

    public ItemCollection(Activity activity, List<GWItemInfoDisplay> gwItemInfoDisplayList) {
        inflater = LayoutInflater.from(activity);
        gwItemInfoDisplays = gwItemInfoDisplayList;
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

        GWItemInfoDisplay currentListData = getItem(position);
        // System.out.println("type " +currentListData.item.gwItemData.detailObject);

        mViewHolder.itemName.setText(currentListData.name);

        return convertView;
    }

    private class MyViewHolder {
        TextView itemName, itemDesc, itemType;
        ImageView itemIcon;

        public MyViewHolder(View item) {
            itemType = (TextView) item.findViewById(R.id.itemSub);
            itemIcon = (ImageView) item.findViewById(R.id.itemIcon);
            itemName = (TextView) item.findViewById(R.id.itemName);
            itemDesc = (TextView) item.findViewById(R.id.itemDesc);
        }
    }
}
