package com.example.guillaume2.gw2applicaton.ToolTip;

import android.view.View;

/**
 * Created by guillaume2 on 06/12/15.
 */
public class ToolTip {

    private int color = 0;
    private String text = null;
    private View view;

    public ToolTip() {}

    public ToolTip addText(String text) {
        this.text = text;
        return this;
    }

    public ToolTip addColor(int color) {
        this.color = color;
        return  this;
    }

    public ToolTip addView(View view) {
        this.view = view;
        return this;
    }

    public View getView() {
        return view;
    }

    public int getColor() {
        return color;
    }

    public String getText() {
        return text;
    }
}
