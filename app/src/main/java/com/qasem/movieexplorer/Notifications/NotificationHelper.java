package com.qasem.movieexplorer.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.qasem.movieexplorer.R;

public class NotificationHelper {

    public static final String CHANNEL_ID = "movie_channel";
    public static final int NOTIFICATION_ID = 1;

    private NotificationManager notificationManager;

    public NotificationHelper(Context context) {

        notificationManager =
                (NotificationManager) context.getSystemService(
                        Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Movie Notifications",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification(Context context,
                                 String title,
                                 String message) {

        Notification notification =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .build();

        notificationManager.notify(
                NOTIFICATION_ID,
                notification
        );
    }
}