package com.aamernabi.moments.utils

import androidx.annotation.ColorInt
import androidx.annotation.Size

@ColorInt
fun parseColor(@Size(min = 1) colorString: String): Int {
    if (colorString[0] != '#') throw IllegalArgumentException("Unknown color")

    var color = colorString.substring(1).toLong(16)
    if (colorString.length == 7) {
        color = color or -0x1000000
    } else {
        require(colorString.length == 9) { "Unknown color" }
    }

    return color.toInt()
}