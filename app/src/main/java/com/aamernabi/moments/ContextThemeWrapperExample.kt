package com.aamernabi.moments

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showMaterialDialog(
    context: Context,
    title: String,
    message: String
) {
    /*val dialogTheme = context.resolveThemeAttr(R.attr.materialAlertDialogTheme)
    val dialogThemeContext = ContextThemeWrapper(context, dialogTheme)*/

    val dialogThemeContext = ContextThemeWrapper(context, R.style.ThemeOverlay_Demo_Dialog)

    @SuppressLint("InflateParams")
    val customView =
        LayoutInflater.from(dialogThemeContext /*don't pass context icon will user color primary*/)
            .inflate(R.layout.finger_print_auth, null)

    MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_Demo_Dialog)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton("Cancel") { p0, _ -> p0.dismiss() }
        .setView(customView)
        .create()
        .show()
}


/**
 * Using the theme resource ID directly works, but then we need to
 * keep this in sync with the theme used by the dialog. We could
 * instead query the value of materialAlertDialogTheme from our theme:
 */
private fun Context.resolveThemeAttr(@AttrRes attr: Int) = TypedValue().let { typedValue ->
    theme.resolveAttribute(attr, typedValue, true)
    typedValue.resourceId
}