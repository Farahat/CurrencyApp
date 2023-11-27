package com.paypay.currencyapp.mappers

import org.junit.Assert.*
import org.junit.Test

class CurrencyLocalMapperTest {
    private val convertMapper = CurrencyLocalMapper()

    @Test
    fun convertLocaleData_CorrectConversion_ReturnsTrue() {
        //arrange
        val currency = jsonLocalData()

        //act
        val conversion = convertMapper.to(currency)

        //assert
        assertTrue(conversion.rates == mockLocalResultCorrect())
    }

    @Test
    fun convertLocaleData_WrongConversion_ReturnsTrue() {
        //arrange
        val currency = jsonLocalData()

        //act
        val conversion = convertMapper.to(currency)

        //assert
        assertFalse(conversion.rates == mockLocalResultWrong())
    }
}