package com.paypay.currencyapp.data.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CurrencyResponse(
    @SerializedName("disclaimer") val disclaimer : String,
    @SerializedName("license") val license : String,
    @SerializedName("timestamp") val timestamp : Long,
    @SerializedName("base") val base : String,
    @SerializedName("rates") val rates: Map<String, Double>
)