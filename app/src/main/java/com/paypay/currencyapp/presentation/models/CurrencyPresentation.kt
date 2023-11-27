package com.paypay.currencyapp.presentation.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class CurrencyPresentation(
    val lastFetchTimeStamp: Long,
    val rates: List<RatePresentation>
)
@Keep
data class RatePresentation(
    @SerializedName("countryIdentifier") val countryIdentifier: String,
    @SerializedName ("currencyRate")val currencyRate: Double,
)