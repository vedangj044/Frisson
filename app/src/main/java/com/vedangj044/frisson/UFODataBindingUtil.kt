package com.vedangj044.frisson

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("setFirstLetterCapital")
fun TextView.setFirstLetterCapital(string: String){
    text = firstLetterCapital(string)
}

// Created separate function to avoid string concatenation warning
private fun firstLetterCapital(string: String): String {
    return string[0].toUpperCase() + string.substring(1)
}

@BindingAdapter("setLocation")
fun TextView.setLocation(item: UFOData) {
    text = String.format(resources.getString(R.string.state_country), item.state, item.country)
}

@BindingAdapter("getDateFromDateTime")
fun TextView.getDateFromDateTime(date: String) {
    text = date.substring(0, 16)
}

@BindingAdapter("setShape")
fun ImageView.setShape(shape: String) {
    setImageResource(when (shape) {
        "Rectangle" -> R.drawable.shape_rectangle
        "Fireball" -> R.drawable.shape_fireball
        "Unknown" -> R.drawable.shape_unknown
        "Oval" -> R.drawable.shape_oval
        "Disk" -> R.drawable.shape_disk
        "Triangle" -> R.drawable.shape_triangle
        "Circle" -> R.drawable.shape_circle
        "Light" -> R.drawable.shape_light
        "Sphere" -> R.drawable.shape_sphere
        "Cigar" -> R.drawable.shape_cigar
        "Teardrop" -> R.drawable.shape_teardrop
        "Cross" -> R.drawable.shape_cross
        "Formation" -> R.drawable.shape_formation
        "Flash" -> R.drawable.shape_flash
        "Changing" -> R.drawable.shape_changing
        "Diamond" -> R.drawable.shape_diamond
        "Egg" -> R.drawable.shape_egg
        "Cylinder" -> R.drawable.shape_cylinder
        "Chevron" -> R.drawable.shape_chevron
        "Cone" -> R.drawable.shape_cone
        "Delta" -> R.drawable.shape_delta
        else -> R.drawable.shape_unknown
    })
}

@BindingAdapter("setImageCoil")
fun setImageCoil(view: ImageView, url: String){

    Glide.with(view).load(url)
        .apply(RequestOptions().placeholder(ColorDrawable(ContextCompat.getColor(view.context, R.color.placeholder_color))))
        .into(view)
}