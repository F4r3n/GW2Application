package com.example.guillaume2.gw2applicaton.ToolTip;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume2 on 06/12/15.
 */
public class ToolTip {

    private int color = 0;
    private String text = null;
    private View view = null;
    private Point p = null;
    private List<Bitmap> images = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    public ToolTip() {
    }

    public ToolTip addText(String text) {
        this.text = textWrapping(text);
        return this;
    }

    public ToolTip addTextIcons(String text) {
        strings.add(textWrapping(text));
        return this;
    }

    private String textWrapping(String text) {
        String[] t = text.split(" ");

        String newText = "";
        int sizeWords = 0;
        for (int i = 0; i < t.length; ++i) {
            sizeWords += t[i].length();
            if (sizeWords > 20) {
                newText += " \n";
                sizeWords = 0;
            }
            newText += " " + t[i];
        }
        return newText;
    }

    public ToolTip addColorText(int color) {
        this.color = color;
        return this;
    }

    public ToolTip addImage(Bitmap bitmap) {
        images.add(bitmap);
        return this;
    }

    public ToolTip addView(View view) {
        this.view = view;
        return this;
    }

    public ToolTip addOffset(Point p) {
        this.p = p;
        return this;
    }

    public View getView() {
        return view;
    }

    public Point getOffset() {
        return p;
    }

    public int getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public List<Bitmap> getImages() { return images;}

    public List<String> getStrings() { return strings;}
}
