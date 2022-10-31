package com.example.moviecatalogproject.presentation.model

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText


class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
            this.clearFocus()
        }
        return super.onKeyPreIme(keyCode, event)
    }

    fun setEditTextsInputSpaceFilter() {
        val filter = InputFilter { source, _, _, _, _, _ ->
            if (source == " " || source.toString().contentEquals("\n")) {
                ""
            } else {
                null
            }
        }
        this.filters = arrayOf(filter)
    }
}