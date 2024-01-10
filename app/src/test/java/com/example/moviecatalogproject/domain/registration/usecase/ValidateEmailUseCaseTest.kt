package com.example.moviecatalogproject.domain.registration.usecase

import com.example.moviecatalogproject.domain.common.model.ErrorType
import com.example.moviecatalogproject.domain.common.validation.usecase.ValidateEmailUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.test.assertEquals

class ValidateEmailUseCaseTest {

    // тест на моки (неполное тестирование)
    @Test
    fun `WHEN usecase invoked THEN invoke validateTextField of EmailValidator`() {
        //arrange
        val emailValidator: EmailValidator = mock()
        val validateEmailUseCase = ValidateEmailUseCase(emailValidator)

        //act
        validateEmailUseCase.execute("")

        //assert
        Mockito.verify(emailValidator).validateTextField("")
    }

    // Структурированное базисное тестирование, классы хороших данных
    @Test
    fun `GIVEN correct email WHEN usecase invoked THEN return ErrorType OK`() {
        //arrange
        val validateEmailUseCase = ValidateEmailUseCase(EmailValidator())
        val correctEmail = "hello@sm.com"

        //act
        val result = validateEmailUseCase.execute(correctEmail)

        //assert
        assertEquals(ErrorType.OK, result)
    }

    // Структурированное базисное тестирование, классы плохих данных
    @Test
    fun `GIVEN empty email WHEN usecase invoked THEN return ErrorType EMPTINESS_ERROR`() {
        //arrange
        val validateEmailUseCase = ValidateEmailUseCase(EmailValidator())
        val email = ""

        //act
        val result = validateEmailUseCase.execute(email)

        //assert
        assertEquals(ErrorType.EMPTINESS_ERROR, result)
    }

    // Структурированное базисное тестирование, классы плохих данных
    @Test
    fun `GIVEN incorrect email without @ WHEN usecase invoked THEN return ErrorType EMAIL_ERROR`() {
        //arrange
        val validateEmailUseCase = ValidateEmailUseCase(EmailValidator())
        val incorrectEmail = "incorrectEmail.d"

        //act
        val result = validateEmailUseCase.execute(incorrectEmail)

        //assert
        assertEquals(ErrorType.EMAIL_ERROR, result)
    }
}