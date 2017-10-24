package com.slapshotapps.bindinghelpers

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso


@BindingAdapter("imageURL")
fun loadImage(view: ImageView, url:String) {
    if(!url.isEmpty()){
        Picasso.with(view.context).load(url).into(view)
    }

}
