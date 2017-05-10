package com.iqiyi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID_BASIC = 2;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.notification_btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();

            }
        });
    }

    private void sendNotification() {
        /**
         * 基本的notification
         * @param view
         */
            Intent intent =  new Intent(this, SecondActivity.class);
            // 构造PendingIntent
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // 创建Notification对象
            Notification.Builder builder = new Notification.Builder(this);
            // 设置Notification的各种属性
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setLargeIcon(BitmapFactory.decodeResource(
                    getResources(), R.mipmap.ic_launcher_round));
            builder.setContentTitle("Basic Notifications");
            builder.setContentText("I am a basic notification");
            builder.setSubText("it is really basic");
            // 通过NotificationManager来发出Notification
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(
                            NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID_BASIC,
                    builder.build());
    }
}
