package com.paypay.currencyapp.data.utlis

//Base always 1 which is 1 USD (Base Currency)
fun convertCurrency(
    amount: Double,
    fromCurrencyRate: Double,
    toCurrencyRate: Double
): Double {
    val fromCurrencyUsdEqual = 1.0 / fromCurrencyRate
    return roundOffDecimal((fromCurrencyUsdEqual * toCurrencyRate) * amount)
}
