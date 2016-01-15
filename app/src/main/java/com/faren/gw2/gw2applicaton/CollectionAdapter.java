package com.faren.gw2.gw2applicaton;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.faren.gw2.gw2applicaton.item.GWItemArmor;
import com.faren.gw2.gw2applicaton.item.GWItemConsumable;

public class CollectionAdapter extends BaseAdapter {

    Bank bank;
    private Activity activity;
    LayoutInflater inflater;

    public CollectionAdapter(Activity activity) {
        this.activity = activity;
        Collection collection = (Collection) activity.getApplication();
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
       // System.out.println("type " +currentListData.item.gwItemData.detailObject);
        switch (currentListData.item.gwItemData.type) {
            case CONSUMABLE:
                GWItemConsumable consumable = currentListData.item.gwItemData.gwItemConsumable;
                mViewHolder.itemType.setText(consumable.description);
                break;
            case ARMOR:
                GWItemArmor armor = currentListData.item.gwItemData.gwItemArmor;
                mViewHolder.itemType.setText(armor.weight_class.toString().toLowerCase());
                break;
            default:
                mViewHolder.itemType.setText("");
                break;

        }

        mViewHolder.itemDesc.setText(currentListData.getDesc());
        mViewHolder.itemName.setText(currentListData.getName());
        mViewHolder.itemIcon.setImageBitmap(currentListData.getIcon());

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
