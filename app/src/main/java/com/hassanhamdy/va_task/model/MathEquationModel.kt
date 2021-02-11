package com.hassanhamdy.va_task.model

import java.text.SimpleDateFormat
import java.util.*

data class MathEquationModel(
    var id: Int = 0,
    var num1: Double = 0.0,
    var num2: Double = 0.0,
    var timeInMillis: Long = 0,
    var result: Double = 0.0,
    var operation: String = ""
) {
//    override fun toString(): String {
//        return """{"id": $id,"num1": $num1, "num2":$num2, "timeInMillis":$timeInMillis, "operation":$operation ,"result":$result }"""
//    }

    override fun toString(): String {
        return "$num1  $operation  $num2 = $result\n${convertMillisToDate()}"
    }

    private fun convertMillisToDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS")
        val calender = Calendar.getInstance()
        calender.timeInMillis = timeInMillis
        return formatter.format(calender.time)
    }
}