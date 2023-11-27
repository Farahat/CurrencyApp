package com.paypay.currencyapp.mappers

import org.junit.Assert.*
import org.junit.Test

class CurrencyRemoteMapperTest {
    private val convertMapper = CurrencyRemoteMapper()

    @Test
    fun convertRemoteData_CorrectConversion_ReturnsTrue() {
        //arrange
        val rates = mockRemoteResponse()

        //act
        val conversion = convertMapper.to(rates)

        //assert
        assertTrue(conversion.rates== mockRemoteResultCorrect())
    }

    @Test
    fun convertRemoteData_WrongConversion_ReturnsTrue() {
        //arrange
        val rates = mockRemoteResponse()

        //act
        val conversion = convertMapper.to(rates)

        //assert
        assertFalse(conversion.rates== mockRemoteResultWrong())
    }
}