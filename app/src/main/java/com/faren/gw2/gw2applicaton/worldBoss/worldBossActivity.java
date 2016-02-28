package com.faren.gw2.gw2applicaton.worldBoss;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.faren.gw2.gw2applicaton.CallerBack;
import com.faren.gw2.gw2applicaton.GW2ItemHelper;
import com.faren.gw2.gw2applicaton.NotificationPublisher;
import com.faren.gw2.gw2applicaton.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class worldBossActivity extends AppCompatActivity implements CallerBack {

    private HashMap<GWWorldBoss, PendingIntent> notificationBoss = new HashMap<>();
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worldboss);
        GW2ItemHelper db = new GW2ItemHelper(this);
        FragmentManager fm = getFragmentManager();
        setTitle("World Boss timer");

        final FragmentBosses list;
        if (fm.findFragmentById(R.id.listFragment) == null) {
            list = new FragmentBosses();
            fm.beginTransaction().add(R.id.listFragment, list).commit();
        } else {
            list = new FragmentBosses();
            fm.beginTransaction().replace(R.id.listFragment, list).commit();
        }

        List<GWWorldBoss> worldBosses = db.selectBosses();
        list.updateData(this, worldBosses);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                list.notifyUpdateData();
                handler.postDelayed(this, 60 * 1000);
            }
        }, 60 * 1000);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String content, long when) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder notification = new Notification.Builder(this)
                    .setWhen(when)
                    .setSmallIcon(R.mipmap.icon)
                    .setContentTitle("GWWorldBoss")
                    .setContentText(content);

            Notification n = notification.build();
            n.defaults |= Notification.DEFAULT_SOUND;
            n.defaults |= Notification.DEFAULT_VIBRATE;
            return n;
        } else {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                    .setWhen(when)
                    .setSmallIcon(R.mipmap.icon)
                    .setContentTitle("GWWorldBoss")
                    .setContentText(content);
            Notification n = notification.build();
            n.defaults |= Notification.DEFAULT_SOUND;
            n.defaults |= Notification.DEFAULT_VIBRATE;
            return n;
        }
    }

    public void addNotificationList(GWWorldBoss boss) {
        String time = boss.time.replaceFirst(" UTC", "");
        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutesT = Integer.parseInt(time.split(":")[1]);
        int hourT = Integer.parseInt(time.split(":")[0]);
        int diffSeconds = -((hour) * 3600 + minutes * 60) + (
                (hourT + TimeZone.getDefault().getRawOffset() / 3600000) * 3600 + minutesT * 60);
        long when = System.currentTimeMillis() + diffSeconds * 1000;

        long futureInMillis = SystemClock.elapsedRealtime() + diffSeconds* 1000;
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        final int _id = (int) System.currentTimeMillis();
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, _id);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, getNotification(boss.nameBoss, when));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, _id, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        notificationBoss.put(boss, pendingIntent);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        Toast.makeText(this, boss.nameBoss + " is followed", Toast.LENGTH_SHORT).show();
    }

    public void removeNotificationList(GWWorldBoss boss) {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(notificationBoss.get(boss));
        notificationBoss.remove(boss);
        Toast.makeText(this, boss.nameBoss + " is removed from the following list", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }
}
