package com.paypay.currencyapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "rates") val rates: String?,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long=System.currentTimeMillis()
)