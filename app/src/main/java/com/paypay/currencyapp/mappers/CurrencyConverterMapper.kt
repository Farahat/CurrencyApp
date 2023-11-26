package com.paypay.currencyapp.mappers

import com.paypay.currencyapp.data.models.RatePresentation
import com.paypay.currencyapp.data.utlis.convertCurrency

class CurrencyConverterMapper :
    ConverterMapper<List<RatePresentation>, Double, Double, List<RatePresentation>> {
    override fun to(
        rates: List<RatePresentation>,
        fromCurrencyRate: Double,
        amount: Double
    ): List<RatePresentation> {
        val convertedRates = ArrayList<RatePresentation>()
        for (rate in rates) {
            convertedRates.add(
                RatePresentation(
                    rate.countryIdentifier,
                    convertCurrency(
                        amount,
                        fromCurrencyRate,
                        rate.currencyRate
                    )
                )
            )
        }
        return convertedRates
    }
}