package com.zaloracasestudy.catastrophic.presentation.ui

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .apply(RequestOptions()
            .centerCrop()
            //use dynamic dimensions if we need to support tablets
            .override(250, 250)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)