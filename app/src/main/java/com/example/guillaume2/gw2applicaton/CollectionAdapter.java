package com.example.guillaume2.gw2applicaton;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by guillaume2 on 17/11/15.
 */
public class CollectionAdapter extends BaseAdapter {

    Bank bank;
    private Activity activity;
    LayoutInflater inflater;
    public CollectionAdapter(Activity activity) {
        this.activity = activity;
        Collection collection = (Collection)activity.getApplication();
        bank = collection.getContainer(0).getBank();
        System.out.println(bank);
        inflater = LayoutInflater.from(activity);
    }
    @Override
    public int getCount() {
        return bank.getBankData().objects.size();
    }

    @Override
    public BankObject getItem(int position) {
        return bank.getBankData().objects.get(position);

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

        BankObject currentListData = getItem(position);

        mViewHolder.itemDesc.setText(currentListData.getName());
        mViewHolder.itemName.setText(currentListData.getDesc());
        mViewHolder.itemIcon.setImageBitmap(currentListData.getIcon());

        return convertView;
    }
    private class MyViewHolder {
        TextView itemName, itemDesc;
        ImageView itemIcon;

        public MyViewHolder(View item) {
            itemIcon = (ImageView) item.findViewById(R.id.itemIcon);
            itemName = (TextView) item.findViewById(R.id.itemName);
            itemDesc = (TextView) item.findViewById(R.id.itemDesc);
        }
    }
}
