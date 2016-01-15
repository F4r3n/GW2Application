package com.faren.gw2.gw2applicaton.Builder;

import android.graphics.Bitmap;
import android.os.Environment;

import com.faren.gw2.gw2applicaton.ImageResource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class TraitFact {
    public enum TYPE {
        ATTRIBUTEADJUST,
        BUFF,
        BUFFCONVERSION,
        COMBOFIELD,
        COMBOFINISHER,
        DAMAGE,
        DISTANCE,
        NODATA,
        NUMBER,
        PERCENT,
        PREFIXEDBUFF,
        RADIUS,
        RANGE,
        RECHARGE,
        TIME,
        UNBLOCKABLE
    }

    public enum FIELD_TYPE {
        AIR,
        DARK,
        FIRE,
        ICE,
        LIGHT,
        LIGHTNING,
        POISON,
        SMOKE,
        ETHERAL,
        WATER,

    }

    public enum FINISHER_TYPE {
        BLAST,
        LEAP,
        PROJECTILE,
        WHIRL
    }


    public TYPE type;
    public String text;
    transient public Bitmap icon;
    public ImageResource iconImage = new ImageResource(50, 50);


    public AttributeAdjust attributeAdjust;
    public Buff buff;
    public BuffConversion buffConversion;
    public ComboField comboField;
    public ComboFinisher comboFinisher;
    public Damage damage;
    public Distance distance;
    public Number number;
    public Percent percent;
    public Radius radius;
    public Range range;
    public Recharge recharge;
    public Time time;
    public Unblockable unblockable;

    public boolean iconExists() {
        return new File(iconImage.iconPath).exists();
    }



    public TraitFact(JSONObject object) {

        try {
            type = TYPE.valueOf(object.getString("type").toUpperCase());

            iconImage.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/GW2App/spe/trait/icon/" +
                    type.toString().toLowerCase() + "-icon.png";
            if (object.has("icon"))
                iconImage.iconUrl = object.getString("icon");
            if (object.has("text"))
                text = object.getString("text");

            switch (type) {
                case ATTRIBUTEADJUST:
                    attributeAdjust = new AttributeAdjust();
                    attributeAdjust.target = object.getString("target");
                    attributeAdjust.value = object.getInt("value");
                    if (text != null)
                        iconImage.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                "/GW2App/spe/trait/icon/" +
                                text.replaceAll(" ", "-").toLowerCase() + "-icon.png";
                    else
                        iconImage.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                "/GW2App/spe/trait/icon/" +
                                type.toString().toLowerCase() + "-icon.png";
                    break;
                case BUFF:
                    buff = new Buff();
                    if (object.has("apply_count"))
                        buff.apply_count = object.getInt("apply_count");

                    if (object.has("description"))
                        buff.description = object.getString("description");
                    if (object.has("duration"))
                        buff.duration = object.getInt("duration");
                    buff.status = object.getString("status");
                    iconImage.iconPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/GW2App/spe/trait/icon/" +
                            buff.status.toLowerCase() + "-icon.png";
                    break;
                case BUFFCONVERSION:
                    buffConversion = new BuffConversion();
                    buffConversion.percent = object.getInt("percent");
                    buffConversion.sources = object.getString("source");
                    buffConversion.target = object.getString("target");
                    break;
                case COMBOFIELD:
                    comboField = new ComboField();
                    if (object.has("field_type"))
                        comboField.fieldType = FIELD_TYPE.valueOf(object.getString("field_type").toUpperCase());
                    break;
                case COMBOFINISHER:
                    comboFinisher = new ComboFinisher();
                    comboFinisher.finisher_type = FINISHER_TYPE.valueOf(object.getString("finisher_type").toUpperCase());
                    comboFinisher.percent = object.getInt("percent");
                    break;
                case DAMAGE:
                    damage = new Damage();
                    if (object.has("hint_count"))
                        damage.hint_count = object.getInt("hint_count");
                    break;
                case DISTANCE:
                    distance = new Distance();
                    distance.distance = object.getInt("distance");
                    break;
                case NODATA:

                    break;
                case NUMBER:
                    number = new Number();
                    if (object.has("value"))
                        number.value = object.getInt("value");
                    break;
                case PERCENT:
                    percent = new Percent();
                    percent.percent = object.getInt("percent");

                    break;
                case PREFIXEDBUFF:

                    break;
                case RADIUS:
                    radius = new Radius();
                    radius.distance = object.getInt("radius");
                    break;
                case RANGE:
                    range = new Range();
                    range.range = object.getInt("range");
                    break;
                case RECHARGE:
                    recharge = new Recharge();
                    recharge.value = object.getInt("value");
                    break;
                case TIME:
                    time = new Time();
                    time.duration = object.getInt("duration");
                    break;
                case UNBLOCKABLE:
                    unblockable = new Unblockable();
                    unblockable.value = object.getBoolean("value");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class AttributeAdjust {
        public String target;
        public int value;
    }

    public class Buff {
        public int duration;
        public String status;
        public String description;
        public int apply_count;
    }

    public class BuffConversion {
        public String sources;
        public int percent;
        public String target;
    }

    public class ComboField {
        public FIELD_TYPE fieldType;
    }

    public class ComboFinisher {
        public int percent;
        public FINISHER_TYPE finisher_type;
    }

    public class Damage {
        public int hint_count;
    }

    public class Distance {
        public int distance;
    }

    public class Number {
        public int value;
    }

    public class Percent {
        public int percent;
    }

    public class Radius {
        public int distance;
    }

    public class Range {
        public int range;
    }

    public class Recharge {
        public int value;
    }

    public class Time {
        public int duration;
    }

    public class NoData {

    }

    public class Unblockable {
        public boolean value = true;
    }
}
