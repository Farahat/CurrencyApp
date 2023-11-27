package com.paypay.currencyapp.mappers

import com.paypay.currencyapp.presentation.models.CurrencyPresentation
import com.paypay.currencyapp.presentation.models.RatePresentation

fun mockRates(): ArrayList<RatePresentation> {
    val mockRates = ArrayList<RatePresentation>()
    mockRates.add(RatePresentation("AED", 3.673))
    mockRates.add(RatePresentation("ANF", 69.194))
    mockRates.add(RatePresentation("ALL", 93.467))
    mockRates.add(RatePresentation("AMD", 401.078))
    return mockRates
}

fun mockResultCorrect(): ArrayList<RatePresentation> {
    val mockRates = ArrayList<RatePresentation>()
    mockRates.add(RatePresentation("AED", 0.393))
    mockRates.add(RatePresentation("ANF", 7.404))
    mockRates.add(RatePresentation("ALL", 10.0))
    mockRates.add(RatePresentation("AMD", 42.912))
    return mockRates
}

fun mockResultWrong(): ArrayList<RatePresentation> {
    val mockRates = ArrayList<RatePresentation>()
    mockRates.add(RatePresentation("AED", 1.393))
    mockRates.add(RatePresentation("ANF", 17.404))
    mockRates.add(RatePresentation("ALL", 20.0))
    mockRates.add(RatePresentation("AMD", 52.912))
    return mockRates
}

fun mockRemoteResponse(): Map<String, Double> {
    val mockRemoteRates = mapOf("AED" to 3.672, "AFN" to 69.211517, "ALL" to 401.183613)
    return mockRemoteRates
}

fun mockRemoteResultCorrect(): ArrayList<RatePresentation> {
    val mockRates = ArrayList<RatePresentation>()
    mockRates.add(RatePresentation("AED", 3.672))
    mockRates.add(RatePresentation("AFN", 69.212))
    mockRates.add(RatePresentation("ALL", 401.184))
    return mockRates
}

fun mockRemoteResultWrong(): ArrayList<RatePresentation> {
    val mockRates = ArrayList<RatePresentation>()
    mockRates.add(RatePresentation("USD", 3.672))
    mockRates.add(RatePresentation("AFN", 169.212))
    mockRates.add(RatePresentation("ALL", 1401.184))
    return mockRates
}

fun jsonLocalData(): String {
    val mockJson =
        "{\"lastFetchTimeStamp\":1701093313023,\"rates\":[{\"countryIdentifier\":\"AED\",\"currencyRate\":3.673},{\"countryIdentifier\":\"AFN\",\"currencyRate\":69.61}]}"
    return mockJson
}

fun mockLocalResultCorrect(): ArrayList<RatePresentation> {
    val mockRates = ArrayList<RatePresentation>()
    mockRates.add(RatePresentation("AED", 3.673))
    mockRates.add(RatePresentation("AFN", 69.61))
    return mockRates
}


fun mockLocalResultWrong(): ArrayList<RatePresentation> {
    val mockRates = ArrayList<RatePresentation>()
    mockRates.add(RatePresentation("USD", 3.673))
    mockRates.add(RatePresentation("AFN", 109.61))
    return mockRates
}