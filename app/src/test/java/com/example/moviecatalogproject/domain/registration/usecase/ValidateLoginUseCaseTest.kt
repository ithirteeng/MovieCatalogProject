package com.example.moviecatalogproject.domain.registration.usecase

import com.example.moviecatalogproject.domain.common.model.ErrorType
import com.example.moviecatalogproject.domain.common.validator.LoginValidator
import com.example.moviecatalogproject.domain.entrance.registration.usecase.ValidateLoginUseCase
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals

class ValidateLoginUseCaseTest {

    //тест на моки (неполное тестирование)
    @Test
    fun `WHEN usecase invoked THEN invoke validateTextField of LoginValidator`() {
        //arrange
        val loginValidator: LoginValidator = Mockito.mock()
        val validateLoginUseCase = ValidateLoginUseCase(loginValidator)

        //act
        validateLoginUseCase.execute("")

        //assert
        Mockito.verify(loginValidator).validateTextField("")
    }

    // Структурированное базисное тестирование, классы хороших данных
    @Test
    fun `GIVEN correct login WHEN usecase invoked THEN return ErrorType OK`() {
        //arrange
        val validateLoginUseCase = ValidateLoginUseCase(LoginValidator())

        //act
        val result = validateLoginUseCase.execute("login")

        //assert
        assertEquals(ErrorType.OK, result)
    }

    // Структурированное базисное тестирование, классы плохих данных
    @Test
    fun `GIVEN empty login WHEN usecase invoked THEN return ErrorType EMPTINESS_ERROR`() {
        //arrange
        val validateLoginUseCase = ValidateLoginUseCase(LoginValidator())

        //act
        val result = validateLoginUseCase.execute("")

        //assert
        assertEquals(ErrorType.EMPTINESS_ERROR, result)
    }
}