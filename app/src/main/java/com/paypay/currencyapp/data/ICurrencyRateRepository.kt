package com.paypay.currencyapp.data

import com.paypay.currencyapp.data.models.CurrencyEntity
import com.paypay.currencyapp.presentation.models.CurrencyPresentation
import com.paypay.currencyapp.data.models.CurrencyResponse
import com.paypay.currencyapp.data.utlis.Resource

interface ICurrencyRateRepository {
    suspend fun getRates(): Resource<CurrencyPresentation>
    suspend fun getRemoteRates(): Resource<CurrencyResponse>
    suspend fun insertRates(rates: CurrencyEntity)
    suspend fun getLocalRates(): Resource<CurrencyEntity>
}