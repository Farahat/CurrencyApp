package com.paypay.currencyapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paypay.currencyapp.domain.GetRatesUseCase
import com.paypay.currencyapp.presentation.models.RatePresentation
import com.paypay.currencyapp.data.utlis.DispatcherProvider
import com.paypay.currencyapp.data.utlis.Resource
import com.paypay.currencyapp.data.utlis.isLocalDataOutDated
import com.paypay.currencyapp.mappers.CurrencyConverterMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase,
    private val convertMapper: CurrencyConverterMapper,
    private val dispatchers: DispatcherProvider
) : ViewModel() {
    private var convertJob: Job? = null
    var lastSelectedCurrency: String = "USD"

    private val lastFetchedCurrencies = ArrayList<String>()
    private val lastFetchedRates = ArrayList<RatePresentation>()
    private var lastFetchedRatesTimeStamp = 0L

    private val _conversion = MutableStateFlow<RatesState>(RatesState.Empty)
    val conversion: StateFlow<RatesState> = _conversion

    init {
        resetCalculations()
    }

    fun onChange(amount: String) {
        _conversion.value = RatesState.Loading
        if (lastFetchedRatesTimeStamp != 0L && lastFetchedRates.isEmpty()
                .not() && isLocalDataOutDated(
                System.currentTimeMillis(),
                lastFetchedRatesTimeStamp
            ).not()
        ) {
            fetchInstantData(amount)
        } else {
            updateInstantData(amount)
        }
    }

    private fun fetchInstantData(amount: String) {
        convertJob?.cancel()
        convertJob = viewModelScope.launch(dispatchers.io) {
            delay(300)
            Log.d("getRates", "fromInstant")
            val fromCurrencyRate = lastFetchedRates.first {
                it.countryIdentifier.lowercase() == lastSelectedCurrency.lowercase()
            }.currencyRate
            val convertedRates = convertMapper.to(
                lastFetchedRates, fromCurrencyRate, amount.toDouble(),
            )
            _conversion.value = RatesState.Success(convertedRates, lastFetchedCurrencies)
        }
    }

    private fun updateInstantData(amount: String) {
        viewModelScope.launch(dispatchers.io) {
            when (val ratesResponse = getRatesUseCase()) {
                is Resource.Error -> {
                    _conversion.value = RatesState.Failure(ratesResponse.message!!)
                }

                is Resource.Success -> {
                    val currency = ratesResponse.data
                    if (currency?.rates != null) {
                        lastFetchedRates.clear()
                        lastFetchedCurrencies.clear()
                        lastFetchedRates.addAll(currency.rates)
                        currency.rates.forEach {
                            lastFetchedCurrencies.add(it.countryIdentifier)
                        }
                        lastFetchedRatesTimeStamp = currency.lastFetchTimeStamp
                        onChange(amount)
                    }
                }
            }
        }
    }

    fun resetCalculations() {
        onChange("1")
    }
}