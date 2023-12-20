package com.example.moviecatalogproject.domain.registration.usecase

import com.example.moviecatalogproject.domain.common.model.ErrorType
import com.example.moviecatalogproject.domain.common.validator.NameValidator
import com.example.moviecatalogproject.domain.entrance.registration.usecase.ValidateNameUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.test.assertEquals

class ValidateNameUseCaseTest {

    @Test
    fun `WHEN usecase invoked THEN invoke validateTextField of NameValidator`() {
        //arrange
        val nameValidator: NameValidator = mock()
        val validateNameUseCase = ValidateNameUseCase(nameValidator)

        //act
        validateNameUseCase.execute("")

        //assert
        Mockito.verify(nameValidator).validateTextField("")
    }

    @Test
    fun `GIVEN correct login WHEN usecase invoked THEN return ErrorType OK`() {
        //arrange
        val validateNameUseCase = ValidateNameUseCase(NameValidator())

        //act
        val result = validateNameUseCase.execute("CorrectName")

        //assert
        assertEquals(ErrorType.OK, result)
    }

    @Test
    fun `GIVEN empty login WHEN usecase invoked THEN return ErrorType EMPTINESS_ERROR`() {
        //arrange
        val validateNameUseCase = ValidateNameUseCase(NameValidator())

        //act
        val result = validateNameUseCase.execute("")

        //assert
        assertEquals(ErrorType.EMPTINESS_ERROR, result)
    }
}