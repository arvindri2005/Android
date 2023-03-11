package com.iitp.anwesha.Notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.iitp.anwesha.R
import com.iitp.anwesha.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Get the order_id from the data payload

        val title = remoteMessage.notification?.title
        val message = remoteMessage.notification?.body
        val play_check = remoteMessage.data["play_check"].toString()

        Log.d("123", play_check.toString())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyChannelName"
            val descriptionText = "My channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        if (play_check == "true") {
            val play_link = remoteMessage.data["play_link"].toString()

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(play_link)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notificationBuilder = NotificationCompat.Builder(this, "1")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.anwesha_logo)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(1, notificationBuilder.build())
        } else {
            val intent = Intent(this, SplashActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val notificationBuilder = NotificationCompat.Builder(this, "1")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.anwesha_logo)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(1, notificationBuilder.build())
        }


    }
}

