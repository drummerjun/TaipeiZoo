package com.scribble.taipeizoo

import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.scribble.taipeizoo.data.models.Plant

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String?) {
    GlideApp.with(view).load(url).placeholder(R.drawable.ic_placeholder).into(view)
}

@BindingAdapter("thumbnails")
fun loadThumbnails(view: ImageView, plant: Plant) {
    val url = when {
        plant.F_Pic01_URL?.isNotBlank() == true -> {
            plant.F_Pic01_URL
        }
        plant.F_Pic02_URL?.isNotBlank() == true -> {
            plant.F_Pic02_URL
        }
        plant.F_Pic03_URL?.isNotBlank() == true -> {
            plant.F_Pic03_URL
        }
        else -> {
            plant.F_Pic04_URL
        }
    }
    GlideApp.with(view).load(url).placeholder(R.drawable.ic_placeholder).into(view)
}

@BindingAdapter("hyperlink")
fun setHyperlink(view: TextView, url: String?) {
    url?.let {
        val html: Spanned = Html.fromHtml(url, Html.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
        view.text = html
    }
}