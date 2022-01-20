package com.hustunique.musica.music;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.hustunique.musica.R;

public class NotificationUtil {
    private static NotificationManager manager;
    private static final int NOTIFICATION_MUSIC_ID = 114514;
    public static void setNotification(Context context,String emotion){
        if(manager == null){
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        /*
          judge if version is above 8.0
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //create channel ID an Name
            String channelID = "whitMusic";
            String channelName = "music notification";
            //set the priority of channel
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, importance);
            manager.createNotificationChannel(notificationChannel);

            RemoteViews biggerView = new RemoteViews(context.getPackageName(),R.layout.biggerview_notification);
            biggerView.setTextViewText(R.id.textView_notification,emotion);
            // biggerView.setString(R.id.textView_notification,"setText","jiji");
            Notification notification = new NotificationCompat.Builder(context, channelID)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setOngoing(true)
                    .setContent(biggerView)
                    .setCustomBigContentView(biggerView)
                    .build();

            /*Notification notification = new NotificationCompat.Builder(context, channelID)
                    .setAutoCancel(true)
                    .setContentTitle("hello,this is title")
                    .setContentText("This is text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .build();
            Log.d("teeee","erer");

             */
            Log.d("hello","erer");
            manager.notify(1, notification);

        }
    }

}
