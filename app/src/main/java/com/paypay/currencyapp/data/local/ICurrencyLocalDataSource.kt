package com.paypay.currencyapp.data.local

import com.paypay.currencyapp.data.models.CurrencyEntity
import com.paypay.currencyapp.data.utlis.Resource

interface ICurrencyLocalDataSource {
    suspend fun insertRates(ratesJson: CurrencyEntity)
    suspend fun getLocalRates(): Resource<CurrencyEntity>
}