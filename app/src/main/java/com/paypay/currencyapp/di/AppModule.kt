package com.paypay.currencyapp.di

import android.content.Context
import androidx.room.Room
import com.paypay.currencyapp.data.remote.CurrencyApi
import com.paypay.currencyapp.data.remote.CurrencyRateRemoteDataSource
import com.paypay.currencyapp.data.remote.ICurrencyRateRemoteDataSource
import com.paypay.currencyapp.data.CurrencyRateRepository
import com.paypay.currencyapp.data.ICurrencyRateRepository
import com.paypay.currencyapp.domain.GetRatesUseCase
import com.paypay.currencyapp.data.local.AppDatabase
import com.paypay.currencyapp.data.local.CurrencyDao
import com.paypay.currencyapp.data.local.CurrencyLocalDataSource
import com.paypay.currencyapp.data.local.ICurrencyLocalDataSource
import com.paypay.currencyapp.data.utlis.DispatcherProvider
import com.paypay.currencyapp.mappers.CurrencyConverterMapper
import com.paypay.currencyapp.mappers.CurrencyLocalMapper
import com.paypay.currencyapp.mappers.CurrencyRemoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://openexchangerates.org/api/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideCurrency(okHttpClient:OkHttpClient): CurrencyApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(CurrencyApi::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext, AppDatabase::class.java, "PayPayCurrency"
        ).build()


    @Provides
    @Singleton
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }

    @Singleton
    @Provides
    fun provideRatesLocalDataSource(dao: CurrencyDao): ICurrencyLocalDataSource =
        CurrencyLocalDataSource(dao)

    @Singleton
    @Provides
    fun provideRatesRemoteDataSource(api: CurrencyApi): ICurrencyRateRemoteDataSource =
        CurrencyRateRemoteDataSource(api)

    @Singleton
    @Provides
    fun provideRatesUseCase(repository: ICurrencyRateRepository): GetRatesUseCase =
        GetRatesUseCase(repository)

    @Singleton
    @Provides
    fun provideRateRepository(
        remoteDataSource: ICurrencyRateRemoteDataSource,
        localDataSource: ICurrencyLocalDataSource,
        localMapper: CurrencyLocalMapper,
        remoteMapper: CurrencyRemoteMapper,
    ): ICurrencyRateRepository =
        CurrencyRateRepository(remoteDataSource, localDataSource, remoteMapper, localMapper)

    @Singleton
    @Provides
    fun provideCurrencyLocalMapper(): CurrencyLocalMapper = CurrencyLocalMapper()

    @Singleton
    @Provides
    fun provideCurrencyRemoteMapper(): CurrencyRemoteMapper = CurrencyRemoteMapper()

    @Singleton
    @Provides
    fun provideCurrencyConverterMapper(): CurrencyConverterMapper = CurrencyConverterMapper()

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}