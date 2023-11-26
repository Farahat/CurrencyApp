package com.paypay.currencyapp.data.remote

import com.paypay.currencyapp.BuildConfig
import com.paypay.currencyapp.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("/latest.json")
    suspend fun getRates(
        @Query("app_id") appId: String = BuildConfig.API_KEY
    ): Response<CurrencyResponse>
}