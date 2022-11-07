package com.example.moviecatalogproject.presentation.common

//noinspection SuspiciousImport
import android.R
import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText


class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if ((event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK)) {
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

    override fun onTextContextMenuItem(id: Int): Boolean {
        val consumed = super.onTextContextMenuItem(id)
        when (id) {
            R.id.cut -> onTextCut()
            R.id.paste -> onTextPaste()
            R.id.copy -> onTextCopy()
        }
        return consumed
    }

    private fun onTextCut() {
    }

    private fun onTextCopy() {
    }

    private fun onTextPaste() {
        this.clearFocus()
    }

    fun onEditTextEditorAction() {
        this.setOnEditorActionListener(OnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_PASTE) {
                this.clearFocus()
                val imm =
                    context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this.windowToken, 0)
                return@OnEditorActionListener true
            }
            false
        })
    }
}