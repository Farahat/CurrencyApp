package com.paypay.currencyapp.mappers

import com.google.gson.Gson
import com.paypay.currencyapp.data.models.CurrencyPresentation
import com.paypay.currencyapp.data.models.RatePresentation

class CurrencyLocalMapper : Mapper<String, CurrencyPresentation> {
    override fun to(t: String): CurrencyPresentation {
        val currencyRate = ArrayList<RatePresentation>()
        val currency = Gson().fromJson(
            t, CurrencyPresentation::class.java
        )
        currency.rates.forEach {
            currencyRate.add(RatePresentation(it.countryIdentifier, it.currencyRate))
        }

        return CurrencyPresentation(currency.lastFetchTimeStamp, currencyRate)
    }
}
