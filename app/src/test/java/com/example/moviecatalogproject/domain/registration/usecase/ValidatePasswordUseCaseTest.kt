package com.example.moviecatalogproject.domain.registration.usecase

import com.example.moviecatalogproject.domain.common.model.ErrorType
import com.example.moviecatalogproject.domain.common.validator.PasswordValidator
import com.example.moviecatalogproject.domain.entrance.registration.usecase.ValidatePasswordUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.test.assertEquals

class ValidatePasswordUseCaseTest {

    @Test
    fun `WHEN usecase invoked THEN invoke validateTextField of PasswordValidator`() {
        //arrange
        val passwordValidator: PasswordValidator = mock()
        val validatePasswordUseCase = ValidatePasswordUseCase(passwordValidator)

        //act
        validatePasswordUseCase.execute("")

        //assert
        Mockito.verify(passwordValidator).validateTextField("")
    }

    @Test
    fun `GIVEN correct password WHEN usecase invoked THEN return ErrorType OK`() {
        //arrange
        val validatePasswordUseCase = ValidatePasswordUseCase(PasswordValidator())

        //act
        val result = validatePasswordUseCase.execute("correct_pass")

        //assert
        assertEquals(ErrorType.OK, result)
    }

    @Test
    fun `GIVEN empty password WHEN usecase invoked THEN return ErrorType EMPTINESS_ERROR`() {
        //arrange
        val validatePasswordUseCase = ValidatePasswordUseCase(PasswordValidator())

        //act
        val result = validatePasswordUseCase.execute("")

        //assert
        assertEquals(ErrorType.EMPTINESS_ERROR, result)
    }

    @Test
    fun `GIVEN two equal passwords WHEN usecase invoked THEN return ErrorType OK`() {
        //arrange
        val validatePasswordUseCase = ValidatePasswordUseCase(PasswordValidator())

        //act
        val result = validatePasswordUseCase.execute("password\npassword")

        //assert
        assertEquals(ErrorType.OK, result)
    }

    @Test
    fun `GIVEN two unequal passwords WHEN usecase invoked THEN return ErrorType PASSWORDS_EQUALITY_ERROR`() {
        //arrange
        val validatePasswordUseCase = ValidatePasswordUseCase(PasswordValidator())

        //act
        val result = validatePasswordUseCase.execute("passwodddddrd\npassword")

        //assert
        assertEquals(ErrorType.PASSWORDS_EQUALITY_ERROR, result)
    }
}