package com.example.guillaume2.gw2applicaton.Builder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guillaume2.gw2applicaton.R;

import java.util.List;

/**
 * Created by guillaume2 on 03/12/15.
 */
public class ChoiceSpeAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Activity activity;
    List<Specialization> specializations;

    public ChoiceSpeAdapter(Activity activity, List<Specialization> spes) {
        this.activity = activity;
        specializations = spes;
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

        return convertView;
    }
    private class MyViewHolder {
        TextView nameSpe;

        public MyViewHolder(View item) {
            nameSpe = (TextView) item.findViewById(R.id.titleSpeChoice);
        }
    }
}
