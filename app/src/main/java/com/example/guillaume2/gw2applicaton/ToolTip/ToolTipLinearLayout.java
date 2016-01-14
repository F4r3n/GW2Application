package com.example.guillaume2.gw2applicaton.ToolTip;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guillaume2.gw2applicaton.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 06/12/15.
 */
public class ToolTipLinearLayout extends LinearLayout implements View.OnClickListener {

    private TextView textView;
    private LinearLayout linearLayoutInside;
    private List<LinearLayout> linearsLayouts;
    private ToolTip toolTip;

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
        linearLayoutInside = (LinearLayout) findViewById(R.id.insideLayout);
    }

    private void addAttributeView(List<Bitmap> images, List<String> strings) {
        linearLayoutInside.removeAllViewsInLayout();
        linearsLayouts.clear();
        int index = 0;
        for (Bitmap image : images) {
            LinearLayout.LayoutParams imParams =
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(imParams);

            ImageView imSex = new ImageView(getContext());
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
        this.toolTip = toolTip;
        setX(0);
        setY(0);
        if (toolTip.getText() != null) {
            textView.setText(toolTip.getText());
        }

        if (toolTip.getColor() != 0) {
            textView.setTextColor(toolTip.getColor());
        }

        if (toolTip.getView() != null) {
            float offsetX = 0.0f;
            float offsetY = 0.0f;
            if (toolTip.getOffset() != null) {
                offsetX = toolTip.getOffset().x;
                offsetY = toolTip.getOffset().y;
            }
            setX(toolTip.getView().getX() + offsetX);
            setY(toolTip.getView().getY() + offsetY);
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
        System.out.println(toolTip.getView().getId());
        toolTip = null;
        if (v.getId() == R.id.toolTip) {
            setVisibility(View.INVISIBLE);
        }
    }
}
