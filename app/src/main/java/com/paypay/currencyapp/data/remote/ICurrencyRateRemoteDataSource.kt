package com.paypay.currencyapp.data.remote

import com.paypay.currencyapp.data.models.CurrencyResponse
import com.paypay.currencyapp.data.utlis.Resource

interface ICurrencyRateRemoteDataSource {
    suspend fun getRates(): Resource<CurrencyResponse>
}