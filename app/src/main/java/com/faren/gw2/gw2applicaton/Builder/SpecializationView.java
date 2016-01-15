package com.faren.gw2.gw2applicaton.Builder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.faren.gw2.gw2applicaton.R;


public class SpecializationView extends LinearLayout {

    private LinearLayout tier1;
    private LinearLayout tier2;
    private LinearLayout tier3;


    /* Programmatic Constructor */
    public SpecializationView(Context context) {
        super(context);
        init(context, null, 0);
    }

    /* An XML Constructor */
    public SpecializationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /* An XML Constructor */
    public SpecializationView(Context context, AttributeSet attrs, int resId) {
        super(context, attrs, resId);
        init(context, attrs, resId);
    }

    /**
     * All initialization happens here!
     */
    private void init(Context context, AttributeSet attrs, int resId) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_specialization, this, true);
    }
}

