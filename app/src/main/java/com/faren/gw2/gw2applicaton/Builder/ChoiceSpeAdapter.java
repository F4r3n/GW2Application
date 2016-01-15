package com.faren.gw2.gw2applicaton.Builder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.faren.gw2.gw2applicaton.R;
import com.faren.gw2.gw2applicaton.Tool.FileManagerTool;

import java.util.ArrayList;
import java.util.List;


public class ChoiceSpeAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Activity activity;
    List<Specialization> specializations;
    List<Bitmap> bitmaps = new ArrayList<>();

    public ChoiceSpeAdapter(Activity activity, List<Specialization> spes) {
        this.activity = activity;
        specializations = spes;
        for(Specialization s : spes) {
            bitmaps.add(FileManagerTool.getImage(s.specializationData.iconImage.iconPath));
        }
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return specializations.size();
    }

    @Override
    public Specialization getItem(int position) {
        return specializations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_choice_spe, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        mViewHolder.nameSpe.setText(getItem(position).specializationData.name);
        mViewHolder.imageView.setImageBitmap(bitmaps.get(position));

        return convertView;
    }
    private class MyViewHolder {
        TextView nameSpe;
        ImageView imageView;

        public MyViewHolder(View item) {
            nameSpe = (TextView) item.findViewById(R.id.titleSpeChoice);
            imageView = (ImageView) item.findViewById(R.id.imageSpeChoice);

        }
    }
}
