package com.example.guillaume2.gw2applicaton.Builder;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.guillaume2.gw2applicaton.R;
import com.example.guillaume2.gw2applicaton.Tool.FileManagerTool;
import com.example.guillaume2.gw2applicaton.ToolTip.ToolTip;
import com.example.guillaume2.gw2applicaton.ToolTip.ToolTipLinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guillaume2 on 05/12/15.
 */
public class SpecializationInterface implements View.OnClickListener {
    private FrameLayout frameLayout;
    private HashMap<Integer, InfoButton> buttonsMinor;
    private HashMap<Integer, InfoButton> buttonsMajor;
    private Activity activity;
    public Specialization specialization;
    private ToolTipLinearLayout toolTipLinearLayout;
    int number = 0;
    int[] traitsChosen = new int[3];

    public SpecializationInterface(Activity activity, FrameLayout frameLayout) {
        for (int i = 0; i < 3; ++i) traitsChosen[i] = -1;
        this.frameLayout = frameLayout;
        toolTipLinearLayout = (ToolTipLinearLayout) frameLayout.findViewById(R.id.toolTip);
        toolTipLinearLayout.hide();
        buttonsMajor = new HashMap<>();
        buttonsMinor = new HashMap<>();
        this.activity = activity;
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    public int[] getValueTraitsSelected() {
        return traitsChosen;
    }

    public void init() {
        buttonsMajor.clear();
        buttonsMinor.clear();
        fillButton();
        setBlackBorder();
    }

    public void setBlackBorder() {
        for (Map.Entry<Integer, InfoButton> entry : buttonsMajor.entrySet()) {
            entry.getValue().imageButton.setBackgroundResource(R.drawable.black_border);
        }
    }

    public void init(int id1, int id2, int id3) {
        buttonsMajor.clear();
        buttonsMinor.clear();
        fillButton();
        setBorder(id1, id2, id3);
    }

    private void fillButton() {
        buttonsMinor.put(R.id.t10, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t10),
                specialization.specializationData.minorTraits.get(0)));
        buttonsMinor.put(R.id.t20, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t20),
                specialization.specializationData.minorTraits.get(1)));
        buttonsMinor.put(R.id.t30, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t30),
                specialization.specializationData.minorTraits.get(2)));


        buttonsMajor.put(R.id.t11, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t11),
                specialization.specializationData.majorTraits.get(0)));
        buttonsMajor.put(R.id.t12, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t12),
                specialization.specializationData.majorTraits.get(1)));
        buttonsMajor.put(R.id.t13, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t13),
                specialization.specializationData.majorTraits.get(2)));

        buttonsMajor.put(R.id.t21, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t21),
                specialization.specializationData.majorTraits.get(3)));
        buttonsMajor.put(R.id.t22, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t22),
                specialization.specializationData.majorTraits.get(4)));
        buttonsMajor.put(R.id.t23, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t23),
                specialization.specializationData.majorTraits.get(5)));

        buttonsMajor.put(R.id.t31, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t31),
                specialization.specializationData.majorTraits.get(6)));
        buttonsMajor.put(R.id.t32, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t32),
                specialization.specializationData.majorTraits.get(7)));
        buttonsMajor.put(R.id.t33, new InfoButton((ImageButton) frameLayout.findViewById(R.id.t33),
                specialization.specializationData.majorTraits.get(8)));


        for (Map.Entry<Integer, InfoButton> entry : buttonsMajor.entrySet()) {
            entry.getValue().imageButton.setOnClickListener(this);
            entry.getValue().imageButton.setBackgroundResource(R.drawable.black_border);
        }

        for (Map.Entry<Integer, InfoButton> entry : buttonsMinor.entrySet()) {
            entry.getValue().imageButton.setOnClickListener(this);
        }


    }

    public void setBorder(int id1, int id2, int id3) {
        System.out.println(id1);
        for (Map.Entry<Integer, InfoButton> entry : buttonsMajor.entrySet()) {
            entry.getValue().imageButton.setBackgroundResource(R.drawable.black_border);
        }
        System.out.println("Load " + id1);
        if (id1 != -1)
            buttonsMajor.get(id1).imageButton.setBackgroundResource(R.drawable.red_border);

        if (id2 != -1)
            buttonsMajor.get(id2).imageButton.setBackgroundResource(R.drawable.red_border);

        if (id3 != -1)
            buttonsMajor.get(id3).imageButton.setBackgroundResource(R.drawable.red_border);

    }

    private void addAttributeToolTip(ToolTip toolTip, TraitFact trait) {
        toolTip.addImage(FileManagerTool.getImage(trait.iconImage.iconPath));
        String text = trait.text + " ";
        System.out.println(trait.type);
        switch (trait.type) {
            case ATTRIBUTEADJUST:
                text = "Increases " + trait.attributeAdjust.target +" "+ Integer.toString(trait.attributeAdjust.value);
                break;
            case BUFF:
                text = trait.buff.description + " " + trait.buff.status;
                break;
            case BUFFCONVERSION:
                text = trait.buffConversion.sources + " " + trait.buffConversion.target
                        + " " + Integer.toString(trait.buffConversion.percent);
                break;
            case COMBOFIELD:
                text = trait.comboField.fieldType.toString();
                break;
            case COMBOFINISHER:
                text = trait.comboFinisher.finisher_type.toString() + " " + trait.comboFinisher.percent;
                break;
            case DAMAGE:
                text += Integer.toString(trait.damage.hint_count);
                break;
            case DISTANCE:
                text = "Radius " + Integer.toString(trait.distance.distance);
                break;
            case NODATA:
                break;
            case NUMBER:
                text += Integer.toString(trait.number.value);
                break;
            case PERCENT:
                text += Integer.toString(trait.percent.percent) +" %";
                break;
            case PREFIXEDBUFF:
                break;
            case RADIUS:
                text += Integer.toString(trait.radius.distance);
                break;
            case RANGE:
                text += Integer.toString(trait.range.range) + " (sec)";
                break;
            case RECHARGE:
                text = "Recharge " + Integer.toString(trait.recharge.value) + " (sec)";
                break;
            case TIME:
                text += Integer.toString(trait.time.duration) + " (sec)";
                break;
            case UNBLOCKABLE:
                text = (trait.unblockable.value) ? "Unblockable" : "Blockable";
                break;
            default:
                break;
        }
        toolTip.addTextIcons(text);
    }


    private void getInformationMinor(int id) {
        String info;
        if (buttonsMinor.get(id).trait.description == null)
            info = buttonsMinor.get(id).trait.name;
        else info = buttonsMinor.get(id).trait.description;
        if (info == null)
            info = "Nothing";

        ToolTip toolTip = new ToolTip()
                .addColorText(Color.WHITE)
                .addText(info)
                .addView((View) frameLayout.findViewById(id).getParent());
        for (TraitFact trait : buttonsMinor.get(id).trait.traits) {
            addAttributeToolTip(toolTip, trait);
        }

        toolTipLinearLayout.addToolTip(toolTip);
    }

    private void getInformationMajor(int id, Point offset) {
        String info;
        if (buttonsMajor.get(id).trait.description == null)
            info = buttonsMajor.get(id).trait.name;
        else info = buttonsMajor.get(id).trait.description;
        if (info == null)
            info = "Nothing";

        ToolTip toolTip = new ToolTip()
                .addColorText(Color.WHITE)
                .addText(info)
                .addView((View) frameLayout.findViewById(id).getParent()).addOffset(offset);
        for (TraitFact trait : buttonsMajor.get(id).trait.traits) {
            addAttributeToolTip(toolTip, trait);
        }
        toolTipLinearLayout.addToolTip(toolTip);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.t10 || id == R.id.t20 || id == R.id.t30) {
            getInformationMinor(id);
        }

        if (id == R.id.t11 || id == R.id.t12 || id == R.id.t13) {
            getInformationMajor(id, null);
            traitsChosen[0] = id;
            buttonsMajor.get(R.id.t11).imageButton.setBackgroundResource(R.drawable.black_border);
            buttonsMajor.get(R.id.t12).imageButton.setBackgroundResource(R.drawable.black_border);
            buttonsMajor.get(R.id.t13).imageButton.setBackgroundResource(R.drawable.black_border);

            buttonsMajor.get(id).imageButton.setBackgroundResource(R.drawable.red_border);
        }

        if (id == R.id.t21 || id == R.id.t22 || id == R.id.t23) {
            getInformationMajor(id, null);
            traitsChosen[1] = id;

            buttonsMajor.get(R.id.t21).imageButton.setBackgroundResource(R.drawable.black_border);
            buttonsMajor.get(R.id.t22).imageButton.setBackgroundResource(R.drawable.black_border);
            buttonsMajor.get(R.id.t23).imageButton.setBackgroundResource(R.drawable.black_border);

            buttonsMajor.get(id).imageButton.setBackgroundResource(R.drawable.red_border);
        }

        if (id == R.id.t31 || id == R.id.t32 || id == R.id.t33) {
            traitsChosen[2] = id;

            getInformationMajor(id, new Point(-20, 0));
            buttonsMajor.get(R.id.t31).imageButton.setBackgroundResource(R.drawable.black_border);
            buttonsMajor.get(R.id.t32).imageButton.setBackgroundResource(R.drawable.black_border);
            buttonsMajor.get(R.id.t33).imageButton.setBackgroundResource(R.drawable.black_border);

            buttonsMajor.get(id).imageButton.setBackgroundResource(R.drawable.red_border);
        }

        toolTipLinearLayout.show();

    }

    public class InfoButton {
        public ImageButton imageButton;
        public Trait trait;

        public InfoButton(ImageButton imageButton, Trait trait) {
            this.imageButton = imageButton;
            this.trait = trait;
        }
    }
}
