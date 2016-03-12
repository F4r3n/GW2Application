package com.faren.gw2.gw2applicaton.ToolTip;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faren.gw2.gw2applicaton.R;

import java.util.ArrayList;
import java.util.List;

public class ToolTipLinearLayout extends LinearLayout implements View.OnClickListener {

    private TextView titleView;
    private TextView textView;
    private LinearLayout linearLayoutInside;
    private List<LinearLayout> linearsLayouts;

    public ToolTipLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public ToolTipLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToolTipLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        linearsLayouts = new ArrayList<>();
        setOnClickListener(this);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tooltip, this);
        textView = (TextView) findViewById(R.id.textToolTip);
        titleView = (TextView) findViewById(R.id.titleToolTip);

        linearLayoutInside = (LinearLayout) findViewById(R.id.insideLayout);
    }

    private void addAttributeView(List<Bitmap> images, List<String> strings) {
        linearLayoutInside.removeAllViewsInLayout();
        linearsLayouts.clear();
        int index = 0;
        LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(100, 100);
        layoutParamsImage.width = (int) getResources().getDimension(R.dimen.imageview_width);
        layoutParamsImage.height = (int) getResources().getDimension(R.dimen.imageview_height);

        for (Bitmap image : images) {
            LinearLayout.LayoutParams imParams =
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(HORIZONTAL);
            linearLayout.setLayoutParams(imParams);

            ImageView imSex = new ImageView(getContext());

            imSex.setLayoutParams(layoutParamsImage);

            imSex.setScaleType(ImageView.ScaleType.FIT_XY);
            imSex.setImageBitmap(image);
            linearLayout.addView(imSex);

            TextView textView = new TextView(getContext());
            if (index < strings.size()) {
                textView.setText(strings.get(index));
                linearLayout.addView(textView);
            }

            linearLayoutInside.addView(linearLayout);
            linearsLayouts.add(linearLayout);
            index++;
        }
    }

    public void addToolTip(ToolTip toolTip) {
        setX(0);
        setY(0);
        if (toolTip.getText() != null) {
            textView.setText(toolTip.getText());
        }
        if (toolTip.getText() != null) {
            titleView.setText(toolTip.getTitle());
        }

        if (toolTip.getColor() != 0) {
            textView.setTextColor(toolTip.getColor());
        }
        if (toolTip.getColor() != 0) {
            titleView.setTextColor(toolTip.getColorTitle());
        }


        if (!toolTip.getImages().isEmpty())
            addAttributeView(toolTip.getImages(), toolTip.getStrings());
        else linearLayoutInside.removeAllViewsInLayout();
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolTip) {
            setVisibility(View.INVISIBLE);
        }
    }
}
