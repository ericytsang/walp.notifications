package com.example.erictsang.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private NotificationManager notificationManager;
    private int notificationCount;
    private int notificationUpdateCount;

    // AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize instance data
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationCount = 0;
        notificationUpdateCount = 0;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // cancel all notifications
        notificationManager.cancelAll();
    }

    // public interface: button onClick methods

    /**
     * creates a new notification in the notification bar.
     *
     * @param unused unused parameter. present, because need to match signature
     *               for {@link Button.OnClickListener}
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public synchronized void insertNotification(View unused)
    {
        // derive new notification ID from notification count. notification ID
        // is needed, so we can update our notifications after they have been
        // created.
        int notificationId = notificationCount++;

        // build the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("Notification #"+notificationId)
                .setContentText("This is the content text! :)")
                .setSmallIcon(R.mipmap.ic_launcher);

        // notify the user
        notificationManager.notify(notificationId,notificationBuilder.build());
    }

    /**
     * updates a random existing notification in the notification bar by
     * adding more content to it.
     *
     * @param unused unused parameter. present, because need to match signature
     *               for {@link Button.OnClickListener}
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public synchronized void updateNotification(View unused)
    {
        // derive random, existing notification ID from notification count.
        // notification ID is needed, so we can update our notifications after
        // they have been created.
        int notificationId = (int) (Math.random()*notificationCount);

        // derive new update notification ID from update notification ID count.
        // this id is only used for show
        int notificationUpdateId = notificationUpdateCount++;

        // build the lame one-line updated notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("Notification #"+notificationId)
                .setContentText("This is update #"+notificationUpdateId)
                .setSmallIcon(R.mipmap.ic_launcher);

        // create, and add the expanded notification things to the notification
        // Sets a title for the Inbox in expanded layout
        notificationBuilder.setStyle(new NotificationCompat.InboxStyle()
                .setBigContentTitle("Big Content Title:")
                .setSummaryText("Summary Text")
                .addLine("added line #1")
                .addLine("added line #2")
                .addLine("added line #3")
                .addLine("added line #4"));

        // notify the user
        notificationManager.notify(notificationId,notificationBuilder.build());
    }

    /**
     * cancels the latest notification.
     *
     * @param unused unused parameter. present, because need to match signature
     *               for {@link Button.OnClickListener}
     */
    public synchronized void deleteNotification(View unused)
    {
        // if there are no notifications to cancel, bail out
        if(notificationCount == 0) return;

        // cancel the latest notification
        int notificationId = --notificationCount;
        notificationManager.cancel(notificationId);
    }
}
