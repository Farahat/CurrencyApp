package com.paypay.currencyapp.mappers

import com.paypay.currencyapp.presentation.models.RatePresentation
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CurrencyConverterMapperTest {
    private val convertMapper = CurrencyConverterMapper()

    private var fromCurrencyRateMock: Double = 0.0
    private var amountMock: Double = 0.0

    @Before
    fun setup() {
        fromCurrencyRateMock = 93.467
        amountMock = 10.0
    }

    @Test
    fun convertValidator_CorrectConversion_ReturnsTrue() {
        //arrange
        val rates = mockRates()
        val result = mockResultCorrect()

        //act
        val conversion = convertMapper.to(rates, fromCurrencyRateMock, amountMock)

        //assert
        assertTrue(conversion == result)
    }


    @Test
    fun convertValidator_WrongConversion_ReturnsTrue() {
        //arrange
        val fromCurrencyRateMock = 93.467
        val amountMock = 10.0
        val rates = mockRates()
        val result = mockResultWrong()

        //act
        val conversion = convertMapper.to(rates, fromCurrencyRateMock, amountMock)

        //assert
        assertFalse(conversion == result)
    }

    @Test
    fun convertValidator_EmptyRates_ReturnsTrue() {
        //arrange
        val result = emptyList<RatePresentation>()

        //act
        val conversion = convertMapper.to(emptyList(), fromCurrencyRateMock, amountMock)

        //assert
        assertTrue(conversion == result)
    }
}