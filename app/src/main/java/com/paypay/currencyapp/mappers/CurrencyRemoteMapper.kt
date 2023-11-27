package com.paypay.currencyapp.mappers

import com.paypay.currencyapp.presentation.models.CurrencyPresentation
import com.paypay.currencyapp.presentation.models.RatePresentation
import com.paypay.currencyapp.data.utlis.roundOffDecimal

class CurrencyRemoteMapper : Mapper<Map<String, Double>, CurrencyPresentation> {
    override fun to(t: Map<String, Double>): CurrencyPresentation {
        val ratesList = ArrayList<RatePresentation>()
        t.forEach { rate ->
            ratesList.add(RatePresentation(rate.key, roundOffDecimal(rate.value)))
        }
        return CurrencyPresentation(System.currentTimeMillis(), ratesList)
    }
}