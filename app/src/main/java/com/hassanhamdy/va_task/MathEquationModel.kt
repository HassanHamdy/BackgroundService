package com.hassanhamdy.va_task

//get object from json ==>  Gson().fromJson(json, MathEquationModel::class.java)
data class MathEquationModel(
    var id: Int = 0,
    var num1: Double = 0.0,
    var num2: Double = 0.0,
    var timeInMillis: Long = 0,
    var result: Double = 0.0,
) {
    override fun toString(): String {
        return """{"id": $id,"num1": $num1, "num2":$num2, "timeInMillis":$timeInMillis, "result":$result }"""
    }
}