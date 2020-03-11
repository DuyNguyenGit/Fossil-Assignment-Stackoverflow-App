package com.fossil.duy.stackoverflow.common

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar!!.title = title
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun View.gone() { this.visibility = View.GONE }

fun View.visible() { this.visibility = View.VISIBLE }

fun View.visibleUnless(isVisible: Boolean) { this.visibility = if (isVisible) View.VISIBLE else View.GONE }

fun DateTime.getFormattedDate(): String = DateTimeFormat.forPattern(DATE_FORMAT).print(this)

private const val DATE_FORMAT = "dd/MM/yyyy"

