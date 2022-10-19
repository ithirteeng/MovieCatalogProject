package com.example.moviecatalogproject.presentation.entrance.model

class PickerState {
    private var male = false
    private var female = false

    fun changeMaleState() {
        male = !male
    }

    fun changeFemaleState() {
        female = !female
    }

    fun getMaleState(): Boolean = male

    fun getFemaleState(): Boolean = female
}