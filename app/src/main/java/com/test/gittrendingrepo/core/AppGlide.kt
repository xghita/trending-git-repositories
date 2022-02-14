package com.test.gittrendingrepo.core


import android.widget.ImageView
import com.bumptech.glide.Glide

object AppGlide {
    fun load(url: String, view: ImageView) {
        Glide.with(view.context)
            .load(url)
            .placeholder(android.R.drawable.alert_light_frame)
            .fallback(android.R.drawable.alert_dark_frame)
            .into(view)
    }
}