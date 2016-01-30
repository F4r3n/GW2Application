package com.faren.gw2.gw2applicaton.Builder;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.faren.gw2.gw2applicaton.CallerBack;
import com.faren.gw2.gw2applicaton.R;
import com.faren.gw2.gw2applicaton.Tool.FileManagerTool;

import java.util.ArrayList;
import java.util.List;


public class DialogSpeChoice extends DialogFragment implements
        AdapterView.OnItemClickListener {


    ListView mylist;
    List<Specialization> specializations;
    BuilderActivity activity;
    CallerBack parent;
    FrameLayout frameLayout;
    int position;
    final int sdk = android.os.Build.VERSION.SDK_INT;

    public DialogSpeChoice() {
    }

    public void init(BuilderActivity buidlderActivity, List<Specialization> spes, CallerBack cb, FrameLayout frameLayout, int pos) {
        specializations = spes;
        this.activity = buidlderActivity;
        parent = cb;
        position = pos;
        this.frameLayout = frameLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_spe_choice, null, false);
        mylist = (ListView) view.findViewById(R.id.list);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mylist.setAdapter(new ChoiceSpeAdapter(activity, specializations));
        mylist.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = (ImageView) frameLayout.findViewById(R.id.background);
        frameLayout.setVisibility(View.VISIBLE);
        String path = specializations.get(position).specializationData.backgroundImage.iconPath;
        //Bitmap b = FileManagerTool.getImage(
        //      specializations.get(position).specializationData.backgroundImage.iconPath);
        /*if (!specializations.get(position).specializationData.elite) {

            System.out.println(FileManagerTool.convertDpToPixels(activity, 1500));
            int w = b.getWidth();
            if(w > 1000) w = 1000;
            b = Bitmap.createBitmap(b, 0, 128, w -500, 128);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 1000, 256, true));
            //FileManagerTool.scaleImage(activity, imageView, b, 1800, 900);
        } else {
            b = Bitmap.createBitmap(b, 0, 40, b.getWidth(), 216);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 1000, 256, true));
        }*/
        System.out.println(path);
        imageView.setImageResource(getResources().getIdentifier(path, "drawable", getActivity().getPackageName()));


        List<ImageButton> buttonsMinor = new ArrayList<>();
        buttonsMinor.add((ImageButton) frameLayout.findViewById(R.id.t10));
        buttonsMinor.add((ImageButton) frameLayout.findViewById(R.id.t20));
        buttonsMinor.add((ImageButton) frameLayout.findViewById(R.id.t30));

        for (int i = 0; i < buttonsMinor.size(); ++i) {
            Bitmap bb = FileManagerTool.getImage(
                    specializations.get(position).specializationData.minorTraits.get(i).iconImage.iconPath);
            buttonsMinor.get(i).setImageBitmap(FileManagerTool.makeTransparent(bb, 255));

        }

        List<ImageButton> buttonsMajor = new ArrayList<>();
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t11));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t12));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t13));

        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t21));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t22));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t23));

        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t31));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t32));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t33));

        for (int i = 0; i < buttonsMajor.size(); ++i) {
            Bitmap bb = FileManagerTool.getImage(
                    specializations.get(position).specializationData.majorTraits.get(i).iconImage.iconPath);
            buttonsMajor.get(i).setImageBitmap(FileManagerTool.makeTransparent(bb, 255));

        }
        activity.notifySpeChoice(this.position, specializations.get(position), position);

        dismiss();
    }


}
