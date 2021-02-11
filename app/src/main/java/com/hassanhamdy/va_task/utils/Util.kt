package com.hassanhamdy.va_task.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hassanhamdy.va_task.backgroundService.AlarmReceiver
import com.hassanhamdy.va_task.model.DelayType
import com.hassanhamdy.va_task.model.MathEquationModel
import java.util.*

class Util {
    fun adjustAlarm(time: Int, timeType: DelayType): Calendar {
        val calendar = Calendar.getInstance()
        if (timeType.type == DelayType.MIN.type)
            calendar.add(Calendar.MINUTE, time)
        else calendar.add(Calendar.SECOND, time)
        return calendar
    }

    fun startAlarm(equation: MathEquationModel, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, equation.timeInMillis, pendingIntent)
    }

    fun getMinMillis(list: ArrayList<MathEquationModel>): MathEquationModel? {
        return list.minByOrNull {
            it.timeInMillis
        }
    }
}