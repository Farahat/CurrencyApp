package com.paypay.currencyapp.domain

import com.paypay.currencyapp.data.ICurrencyRateRepository
import com.paypay.currencyapp.data.models.CurrencyPresentation
import com.paypay.currencyapp.data.utlis.Resource
import javax.inject.Inject

class GetRatesUseCase @Inject constructor(private val repository: ICurrencyRateRepository) {
    suspend operator fun invoke(): Resource<CurrencyPresentation> {
        return repository.getRates()
    }
}