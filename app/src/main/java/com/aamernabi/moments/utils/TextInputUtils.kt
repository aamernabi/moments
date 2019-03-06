package com.aamernabi.moments.utils

import android.widget.EditText

val notEmpty: (String) -> Boolean = { it.isNotEmpty() }
val ofLength: (String, Int) -> Boolean = { str, length -> str.length == length  }

fun EditText.validate(block: (text: String) -> Boolean): Boolean {
    val text = text?.toString() ?: ""
    return block(text)
}