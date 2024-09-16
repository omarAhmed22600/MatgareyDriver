package com.brandsin.driver.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.brandsin.driver.R;
import com.brandsin.driver.ui.activity.auth.AuthActivity;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class NotificationFirebaseMessagingService extends FirebaseMessagingService {

    boolean showNotifcation;
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotifcation = PrefMethods.INSTANCE.getIsNotificationsEnabled(this);
        //Check if Notn are Enabled?
        if (showNotifcation) {
            //Show Notification
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            Map<String, String> data = remoteMessage.getData();
            sendNotification(notification, data);
        }
    }

    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo1);

        if (intent == null) {
            intent = new Intent();
        }

        if (data != null) {
            if (data.get("order_id") != null) {
                intent = new Intent(this, AuthActivity.class);
                intent.putExtra("order_id", Integer.valueOf(data.get("order_id")));
            }
//            else if(data.get("notification") != null && !data.get("notification").equals("0")){
//                intent = new Intent(this, HomeActivity.class);
//                intent.putExtra("notification", data.get("notification"));
//            }
            else {
                intent = new Intent(this, AuthActivity.class);
            }
        }
//        } else {
//            intent = new Intent(this, AuthActivity.class);
//        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        if (notification != null) {

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                    .setContentTitle(notification.getTitle())
                    .setContentText(notification.getBody())
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setContentInfo(notification.getTitle())
                    .setLargeIcon(icon)
                    .setColor(getResources().getColor(R.color.color_primary))
                    .setLights(Color.RED, 1000, 300)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.drawable.app_logo1)
                    .setStyle(new NotificationCompat.InboxStyle());

            //notificationBuilder.setSound(Uri.parse("android.resource://"+ getApplicationContext().getPackageName() + "/" + R.raw.notification));
            //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))


            try {
                String picture_url = data.get("picture_url");
                if (picture_url != null && !"".equals(picture_url)) {
                    URL url = new URL(picture_url);
                    Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    notificationBuilder.setStyle(
                            new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                    );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification no=new Notification();

            MediaPlayer mp= MediaPlayer.create(getApplicationContext(), R.raw.notification);
            mp.start();
            notificationManager.notify(73195, notificationBuilder.build());

            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.notification);  //Here is FILE_NAME is the name of file that you want to play

            // Notification Channel is required for Android O and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);

                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();

                channel.setDescription("channel description");
                channel.setShowBadge(true);
                channel.canShowBadge();
                channel.enableLights(true);
                channel.setSound(sound, attributes);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
                notificationManager.createNotificationChannel(channel);
            }


             //Notification not=notificationBuilder.build();

            no.sound=Uri.parse("android.resource://"+ getApplicationContext().getPackageName() + "/" + R.raw.notification);
            no.defaults |= Notification.DEFAULT_VIBRATE;
            //notificationManager.notify(0,notificationBuilder.build() );
        }
    }
}