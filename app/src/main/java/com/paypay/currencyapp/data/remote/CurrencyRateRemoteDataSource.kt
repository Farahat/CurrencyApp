package com.paypay.currencyapp.data.remote

import com.paypay.currencyapp.data.models.CurrencyResponse
import com.paypay.currencyapp.data.utlis.Resource
import javax.inject.Inject

class CurrencyRateRemoteDataSource @Inject constructor(private val api: CurrencyApi) :
    ICurrencyRateRemoteDataSource {
    override suspend fun getRates(): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        }catch (e:Exception){
            Resource.Error(e.message?:"An Error Occurred")
        }
    }
}
