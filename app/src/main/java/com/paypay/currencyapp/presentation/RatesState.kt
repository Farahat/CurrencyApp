package com.paypay.currencyapp.presentation

import com.paypay.currencyapp.presentation.models.RatePresentation

sealed class RatesState {
    class Success(val rates: List<RatePresentation>, val currencies: List<String>) : RatesState()
    class Failure(val errorText: String) : RatesState()
    object Loading : RatesState()
    object Empty : RatesState()
}