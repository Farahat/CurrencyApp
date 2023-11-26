package com.paypay.currencyapp.data

import android.util.Log
import com.google.gson.Gson
import com.paypay.currencyapp.data.local.CurrencyEntity
import com.paypay.currencyapp.data.local.ICurrencyLocalDataSource
import com.paypay.currencyapp.data.models.CurrencyPresentation
import com.paypay.currencyapp.data.remote.ICurrencyRateRemoteDataSource
import com.paypay.currencyapp.data.utlis.Resource
import com.paypay.currencyapp.data.utlis.isLocalDataOutDated
import com.paypay.currencyapp.mappers.CurrencyLocalMapper
import com.paypay.currencyapp.mappers.CurrencyRemoteMapper
import javax.inject.Inject

class CurrencyRateRepository @Inject constructor(
    private val remoteDataSource: ICurrencyRateRemoteDataSource,
    private val localDataSource: ICurrencyLocalDataSource,
    private val remoteRatesMapper: CurrencyRemoteMapper,
    private val localRatesMapper: CurrencyLocalMapper
) : ICurrencyRateRepository {

    override suspend fun getRates(): Resource<CurrencyPresentation> {
        val isLocalDataOutDated = isLocalDataOutDated(
            System.currentTimeMillis(), getLocalRates().data?.timeStamp ?: 0
        )
        return if (isLocalDataOutDated) {
            Log.d("getRates", "fromRemote")
            when (val ratesResponse = getRemoteRates()) {
                is Resource.Error -> Resource.Error(ratesResponse.message ?: "")
                is Resource.Success -> {
                    val currency = remoteRatesMapper.to(ratesResponse.data!!.rates)
                    insertRates(CurrencyEntity(1, Gson().toJson(currency)))
                    Resource.Success(currency)
                }
            }
        } else {
            Log.d("getRates", "fromLocal")
            when (val ratesResponse = getLocalRates()) {
                is Resource.Error -> Resource.Error(ratesResponse.message ?: "")
                is Resource.Success -> {
                    val currency = localRatesMapper.to(ratesResponse.data!!.rates!!)
                    Resource.Success(currency)
                }
            }
        }
    }

    override suspend fun getRemoteRates() = remoteDataSource.getRates()

    override suspend fun insertRates(ratesJson: CurrencyEntity) =
        localDataSource.insertRates(ratesJson)

    override suspend fun getLocalRates(): Resource<CurrencyEntity> = localDataSource.getLocalRates()

}