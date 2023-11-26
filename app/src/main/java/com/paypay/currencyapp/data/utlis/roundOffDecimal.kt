package com.paypay.currencyapp.data.utlis

import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat

fun roundOffDecimal(number: Double): Double {
    var outPut: Double = 0.0
    try {
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        outPut = df.format(number).toDouble()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return outPut
}