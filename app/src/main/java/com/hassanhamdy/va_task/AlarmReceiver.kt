package com.hassanhamdy.va_task

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(
            "HASSAN",
            "RECEIVER NOW" +Calendar.getInstance().timeInMillis
        )

        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val notificationUtils = NotificationUtils(context)
        val notification = notificationUtils.getNotificationBuilder().build()
        var notificationId = 150
        notificationUtils.getManager().notify(++notificationId, notification)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val newIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, newIntent, 0)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 1)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Log.d("HASSAN", "RECEIVER ALARM AGAIN" + calendar.timeInMillis)
    }
}