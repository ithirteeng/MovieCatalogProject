package com.example.moviecatalogproject.presentation.helper

object DateConverter {

    fun convertToBackendFormat(date: String): String {
        val dateArray = date.split('.')
        return (dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0])
    }

    fun convertToNormalForm(date: String): String {
        val dateArray = date.split('-')
        return dateArray[2].substring(0, 2) + "." + dateArray[1] + "." + dateArray[0]
    }

}