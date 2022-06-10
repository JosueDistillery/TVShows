package com.distillery.tvshows.utils.extensions

import android.graphics.Typeface
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView

fun TextView.setBoldTitle(title: String, description: String) {
    val spannableString = SpannableString(title + System.getProperty("line.separator") + description)
    spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    setText(spannableString)
}

fun TextView.setHtmlText(html: String) {
    text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
}

fun TextView.setVisibility(visible: Boolean) {
    visibility.also {
        if (visible)
            View.VISIBLE
        else
            View.GONE
    }
}