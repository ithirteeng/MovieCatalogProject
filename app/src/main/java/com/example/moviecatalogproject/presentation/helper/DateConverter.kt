package com.example.moviecatalogproject.presentation.helper

object DateConverter {
    fun convertDateToCorrectForm(date: String): String {
        val dateArray = date.split('.')
        return (dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0])
    }
}