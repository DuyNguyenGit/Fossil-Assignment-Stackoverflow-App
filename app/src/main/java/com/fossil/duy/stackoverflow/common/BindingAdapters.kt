package com.fossil.duy.stackoverflow.common

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import org.joda.time.DateTime


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions.circleCropTransform())
            .into(view)
    }
}

@BindingAdapter("date")
fun bindDateTime(view: TextView, dateTime: DateTime?) {
    view.text = dateTime?.getFormattedDate() ?: ""
}