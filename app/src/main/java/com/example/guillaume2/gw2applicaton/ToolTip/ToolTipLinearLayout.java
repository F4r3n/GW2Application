package com.example.guillaume2.gw2applicaton.ToolTip;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guillaume2.gw2applicaton.R;

/**
 * Created by guillaume2 on 06/12/15.
 */
public class ToolTipLinearLayout extends LinearLayout implements View.OnClickListener {

    private TextView textView;
    private ToolTip toolTip;

    public ToolTipLinearLayout(Context context) {
        super(context);
        init(context);
        System.out.println("Init");
    }

    public ToolTipLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        System.out.println("Init2");
    }

    public ToolTipLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        System.out.println("Init3");
    }

    private void init(Context context) {
        setOnClickListener(this);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tooltip, this);
        textView = (TextView) findViewById(R.id.textToolTip);
    }

    public void addToolTip(ToolTip toolTip) {
        this.toolTip = toolTip;
        setX(0);
        setY(0);
        if (toolTip.getText() != null) {
            textView.setText(toolTip.getText());
        }

        if (toolTip.getColor() != 0) {
            setBackgroundColor(toolTip.getColor());
        }

        if(toolTip.getView() != null) {
            float offsetX = 0.0f;
            float offsetY = 0.0f;
            if(toolTip.getOffset() != null) {
                offsetX = toolTip.getOffset().x;
                offsetY = toolTip.getOffset().y;
            }
            setX(toolTip.getView().getX() + offsetX);
            setY(toolTip.getView().getY() + offsetY);
        }


    }

    public void show() {
        setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        System.out.println(toolTip.getView().getId());
        toolTip = null;
        if(v.getId() == R.id.toolTip) {
            setVisibility(View.INVISIBLE);
        }
    }
}
