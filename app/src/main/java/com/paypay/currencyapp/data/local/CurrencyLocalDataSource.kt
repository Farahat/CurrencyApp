package com.paypay.currencyapp.data.local

import android.util.Log
import com.paypay.currencyapp.data.utlis.Resource
import javax.inject.Inject

class CurrencyLocalDataSource @Inject constructor(private val dao: CurrencyDao) :
    ICurrencyLocalDataSource {
    override suspend fun insertRates(ratesJson: CurrencyEntity) {
        try {
            dao.insertRates(ratesJson)
        } catch (e: Exception) {
            Log.d("exxx", e.message.toString())
        }
    }

    override suspend fun getLocalRates(): Resource<CurrencyEntity> {
        return try {
            val localRates = dao.getLocalRates()
            if (localRates.rates.isNullOrEmpty().not()) {
                Resource.Success(localRates)
            } else {
                Resource.Error("An Error Occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error Occurred")
        }
    }
}