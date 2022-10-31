package com.example.moviecatalogproject.presentation.model

class PickerState {
    private var male = false
    private var female = false

    fun changeMaleState() {
        male = !male
    }

    fun changeFemaleState() {
        female = !female
    }

    fun setMaleState(state: Boolean) {
        male = state
    }

    fun setFemaleState(state: Boolean) {
        female = state
    }

    fun getMaleState(): Boolean = male

    fun getFemaleState(): Boolean = female

}