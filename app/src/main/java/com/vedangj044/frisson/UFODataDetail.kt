package com.vedangj044.frisson

import com.squareup.moshi.Json
import java.util.*

data class UFODataDetail (

    @Json(name = "city")
    val city: String,

    @Json(name = "comments")
    val comments: String,

    @Json(name = "country")
    val country: String,

    @Json(name = "date added")
    val date_added: Date,

    @Json(name = "id")
    val id: Int,

    @Json(name = "image")
    val image: String,

    @Json(name = "keywords")
    val keywords: String,

    @Json(name = "lat")
    val lat: Double,

    @Json(name = "long")
    val long: Double,

    @Json(name = "seconds")
    val seconds: Double,

    @Json(name = "shape")
    val shape: String,

    @Json(name = "sighting_date")
    val sightingDate: String,

    @Json(name = "state")
    val state: String,

    @Json(name = "time")
    val time: String
)