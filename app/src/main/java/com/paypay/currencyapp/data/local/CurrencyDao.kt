package com.paypay.currencyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM CurrencyEntity")
    suspend fun getLocalRates(): CurrencyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: CurrencyEntity)
}
