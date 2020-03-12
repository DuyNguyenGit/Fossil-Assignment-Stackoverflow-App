package com.fossil.duy.stackoverflow.common

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.loadCircleImage(imageUrl)
    }
}

@BindingAdapter("date")
fun bindDateTime(view: TextView, dateTime: DateTime?) {
    view.text = dateTime?.getFormattedDate() ?: ""
}

@BindingAdapter("creationDate")
fun bindCreationDate(view: TextView, millis: Long?) {
    view.text = DateTime(millis, DateTimeZone.UTC).getFormattedDate()
}