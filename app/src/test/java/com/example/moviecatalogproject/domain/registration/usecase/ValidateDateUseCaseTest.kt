package com.example.moviecatalogproject.domain.registration.usecase

import com.example.moviecatalogproject.domain.common.model.ErrorType
import com.example.moviecatalogproject.domain.entrance.registration.usecase.ValidateDateUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import kotlin.test.assertEquals

class ValidateDateUseCaseTest {

    // тест на моки (неполное)
    @Test
    fun `WHEN usecase invoked THEN invoke validateTextField of DateValidator`() {
        //arrange
        val dateValidator: DateValidator = mock()
        val validateDateUseCase = ValidateDateUseCase(dateValidator)

        //act
        validateDateUseCase.execute("")

        //assert
        Mockito.verify(dateValidator).validateTextField("")
    }

    // Структурированное базисное тестирование, классы хороших данных
    @Test
    fun `GIVEN correct date in correct format WHEN usecase invoked THEN return ErrorType OK`() {
        //arrange
        val validateDateUseCase = ValidateDateUseCase(DateValidator())
        val date = "10.12.2003"

        //act
        val result = validateDateUseCase.execute(date)

        //assert
        assertEquals(ErrorType.OK, result)
    }

    // Структурированное базисное тестирование, классы плохих данных, граничные значения
    @Test
    fun `GIVEN empty date string WHEN usecase invoked THEN return ErrorType EMPTINESS_ERROR `() {
        //arrange
        val validateDateUseCase = ValidateDateUseCase(DateValidator())
        val date = ""

        //act
        val result = validateDateUseCase.execute(date)

        //assert
        assertEquals(ErrorType.EMPTINESS_ERROR, result)
    }

    // Структурированное базисное тестирование, классы плохих данных, граничные значения
    @Test
    fun `GIVEN incorrect date in correct format WHEN usecase invoked THEN return ErrorType DATE_ERROR `() {
        //arrange
        val validateDateUseCase = ValidateDateUseCase(DateValidator())
        val date = "12.41.2003"

        //act
        val result = validateDateUseCase.execute(date)

        //assert
        assertEquals(ErrorType.DATE_ERROR, result)
    }

    // Структурированное базисное тестирование, классы плохих данных
    @Test
    fun `GIVEN date in incorrect format WHEN usecase invoked THEN return ErrorType DATE_ERROR `() {
        //arrange
        val validateDateUseCase = ValidateDateUseCase(DateValidator())
        val date = "12.41.03"

        //act
        val result = validateDateUseCase.execute(date)

        //assert
        assertEquals(ErrorType.DATE_ERROR, result)
    }

}