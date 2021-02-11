package com.hassanhamdy.va_task.backgroundService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.hassanhamdy.va_task.MainActivity
import com.hassanhamdy.va_task.model.MathEquationModel
import com.hassanhamdy.va_task.model.OperationType
import com.hassanhamdy.va_task.utils.NotificationUtils
import com.hassanhamdy.va_task.utils.SharedPref
import com.hassanhamdy.va_task.utils.Util
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    var pendingList: ArrayList<MathEquationModel> = ArrayList()
    var servedList: ArrayList<MathEquationModel> = ArrayList()

    override fun onReceive(context: Context, intent: Intent) {
        SharedPref.instance.initSharedPref(context)
        fillLists()

        val servingItem = Util().getMinMillis(pendingList)
        val result = doMathOperation(
            servingItem?.num1 ?: 0.0,
            servingItem?.num2 ?: 0.0,
            OperationType.valueOf(servingItem?.operation ?: "ADD")
        )
        val servedItem = pendingList.find {
            it.id == servingItem?.id
        }

        if (servedItem != null) {
            servedItem.result = result
            pendingList.remove(servedItem)
            servedList.add(servedItem)

            SharedPref.instance.putPendingListJson(Gson().toJson(pendingList))
            SharedPref.instance.putServedListJson(Gson().toJson(servedList))

            context.startActivity(Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            })

            // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
            val notificationUtils = NotificationUtils(context, servedItem.toString())
            val notification = notificationUtils.getNotificationBuilder().build()
            notificationUtils.getManager().notify(servedItem.id, notification)
        }

        val comingServedItem = Util().getMinMillis(pendingList)
        if (comingServedItem != null) {
            Util().startAlarm(comingServedItem, context)
        }
    }

    private fun fillLists() {
        //get pending lists from sharedPref
        val pendingJson = SharedPref.instance.getPendingListJson()
        if (pendingJson != "") {
            pendingList.clear()
            pendingList.addAll(
                Gson().fromJson(pendingJson, Array<MathEquationModel>::class.java).asList()
            )
        }


        //get served lists from sharedPref
        val servedJson = SharedPref.instance.getServedListJson()
        if (servedJson != "") {
            servedList.clear()
            servedList.addAll(
                Gson().fromJson(servedJson, Array<MathEquationModel>::class.java).asList()
            )
        }
    }

    private fun doMathOperation(num1: Double, num2: Double, operation: OperationType): Double {
        return when (operation.type) {
            OperationType.ADD.type -> num1 + num2
            OperationType.MUL.type -> num1 * num2
            OperationType.DIV.type -> num1 / num2
            OperationType.SUB.type -> num1 - num2
            else -> 0.0
        }
    }
}