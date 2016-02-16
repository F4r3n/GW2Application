package com.faren.gw2.gw2applicaton.dyeDisplay;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        final MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_dye, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        final GWDyeDisplay currentListData = getItem(position);
        // System.out.println("type " +currentListData.item.gwItemData.detailObject);
        mViewHolder.title.setText(currentListData.name);


        int valuePrice = Integer.parseInt(currentListData.trading_value);
        final int goldPrice = valuePrice / 10000;
        final int silverPrice = (valuePrice % 10000) / 100;
        final int copperPrice = valuePrice - goldPrice * 10000 - silverPrice * 100;
        //System.out.println("Item " + mViewHolder.goldValue + " " + goldPrice + " " + silverPrice + " " + copperPrice);


        activity.runOnUiThread(new Runnable() {
            public void run() {
                mViewHolder.gp.setText(String.format("%d", goldPrice));
                mViewHolder.sp.setText(String.format("%d", silverPrice));
                mViewHolder.cp.setText(String.format("%d", copperPrice));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mViewHolder.color1.setBackground(new ColorDrawable(currentListData.rgbCloth));
                    mViewHolder.color2.setBackground(new ColorDrawable(currentListData.rgbLeather));
                    mViewHolder.color3.setBackground(new ColorDrawable(currentListData.rgbMetal));
                } else {
                    mViewHolder.color1.setBackgroundDrawable(new ColorDrawable(currentListData.rgbCloth));
                    mViewHolder.color2.setBackgroundDrawable(new ColorDrawable(currentListData.rgbLeather));
                    mViewHolder.color3.setBackgroundDrawable(new ColorDrawable(currentListData.rgbMetal));
                }

            }
        });


        return convertView;
    }


    private class MyViewHolder {

        ImageView color1;
        ImageView color2;
        ImageView color3;

        TextView title;

        TextView gp, sp, cp;

        public MyViewHolder(View item) {

            title = (TextView) item.findViewById(R.id.itemName);

            color1 = (ImageView) item.findViewById(R.id.itemImage1);
            color2 = (ImageView) item.findViewById(R.id.itemImage2);
            color3 = (ImageView) item.findViewById(R.id.itemImage3);

            gp = (TextView) item.findViewById(R.id.goldPrice);
            sp = (TextView) item.findViewById(R.id.silverPrice);
            cp = (TextView) item.findViewById(R.id.copperPrice);
        }
    }
}


