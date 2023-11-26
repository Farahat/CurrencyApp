package com.paypay.currencyapp.data.utlis

import java.util.concurrent.TimeUnit

const val FreshDataPeriod = 30 // minutes

fun isLocalDataOutDated(endTime: Long, startTime: Long): Boolean {
    val milliseconds = endTime - startTime
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    return minutes > FreshDataPeriod
}