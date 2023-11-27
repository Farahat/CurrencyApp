package com.paypay.currencyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paypay.currencyapp.data.models.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}