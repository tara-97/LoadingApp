package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
private val NOTIFICATION_ID = 0
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context, fileName: String, status:String, url:String) {
    val detailIntent = Intent(applicationContext, DetailActivity::class.java)
            .putExtra("filename", fileName)
            .putExtra("status", status)
            .putExtra("url", url)

    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            detailIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.details_notification_channel_id)

    )
            .setSmallIcon(R.drawable.ic_assistant_black_24dp)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .addAction(R.drawable.ic_assistant_black_24dp,applicationContext.getString(R.string.notification_action),contentPendingIntent)
            .setAutoCancel(true)
            notify(NOTIFICATION_ID,builder.build())
}