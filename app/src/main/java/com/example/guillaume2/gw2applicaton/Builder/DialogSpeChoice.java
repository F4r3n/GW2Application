package com.example.guillaume2.gw2applicaton.Builder;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.guillaume2.gw2applicaton.BuilderActivity;
import com.example.guillaume2.gw2applicaton.CallerBack;
import com.example.guillaume2.gw2applicaton.R;
import com.example.guillaume2.gw2applicaton.Tool.FileManagerTool;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by guillaume2 on 03/12/15.
 */
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

    private void scaleImage(ImageView view, Bitmap b, int w, int h) throws NoSuchElementException {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = b;


        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int boundingX = dpToPx(w);
        int boundingY = dpToPx(h);

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) boundingX) / width;
        float yScale = ((float) boundingY) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);


        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);

    }

    private int dpToPx(int dp) {
        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public float convertDpToPixels(float dp) {
        Resources resources = activity.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = (ImageView) frameLayout.findViewById(R.id.background);
        frameLayout.setVisibility(View.VISIBLE);
        Bitmap b = FileManagerTool.getImage(
                specializations.get(position).specializationData.backgroundImage.iconPath);
        if (!specializations.get(position).specializationData.elite) {

            System.out.println(convertDpToPixels(1500));
            b = Bitmap.createBitmap(b, 0, 128, 800, 128);
            scaleImage(imageView, b, 1800, 900);
        } else {
            b = Bitmap.createBitmap(b, 0, 40, 948, 216);
            scaleImage(imageView, b, 1900, 256);
        }


        List<ImageButton> buttonsMinor = new ArrayList<>();
        buttonsMinor.add((ImageButton) frameLayout.findViewById(R.id.t10));
        buttonsMinor.add((ImageButton) frameLayout.findViewById(R.id.t20));
        buttonsMinor.add((ImageButton) frameLayout.findViewById(R.id.t30));

        for (int i = 0; i < buttonsMinor.size(); ++i) {
            Bitmap bb = FileManagerTool.getImage(
                    specializations.get(position).specializationData.minorTraits.get(i).iconImage.iconPath);
            buttonsMinor.get(i).setImageBitmap(makeTransparent(bb, 255));

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
           buttonsMajor.get(i).setImageBitmap(makeTransparent(bb, 255));

        }
        activity.notifySpeChoice(this.position, specializations.get(position));

        dismiss();
    }

    public Bitmap makeTransparent(Bitmap src, int value) {
        if(value == 255) return src;
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap transBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        // config paint
        final Paint paint = new Paint();
        paint.setAlpha(value);
        canvas.drawBitmap(src, 0, 0, paint);
        return transBitmap;
    }


}
