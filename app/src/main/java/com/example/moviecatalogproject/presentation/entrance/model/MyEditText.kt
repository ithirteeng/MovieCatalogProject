package com.example.moviecatalogproject.presentation.entrance.model

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent

class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatEditText(context, attrs) {
    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
            this.clearFocus()
        }
        return super.onKeyPreIme(keyCode, event)
    }
}