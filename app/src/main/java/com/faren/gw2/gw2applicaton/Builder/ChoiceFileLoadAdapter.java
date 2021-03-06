package com.faren.gw2.gw2applicaton.Builder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.faren.gw2.gw2applicaton.R;

import java.util.List;


public class ChoiceFileLoadAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Activity activity;
    List<String> files;

    public ChoiceFileLoadAdapter(Activity activity, List<String> files) {
        this.activity = activity;
        this.files = files;

        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public String getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_file, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        String currentValue = getItem(position);
        String pro = currentValue.substring(0, currentValue.indexOf("_"));
        Professions professions = Professions.valueOf(pro);
        String name = currentValue.substring(currentValue.indexOf("_") + 1, currentValue.length());
        mViewHolder.nameSpe.setText(name);
        mViewHolder.imageView.setImageResource(BuilderActivity.getIdIcon(professions));

        return convertView;
    }

    private class MyViewHolder {
        TextView nameSpe;
        ImageView imageView;

        public MyViewHolder(View item) {
            nameSpe = (TextView) item.findViewById(R.id.file_name_load);
            imageView = (ImageView) item.findViewById(R.id.image_name_load);
        }
    }
}
