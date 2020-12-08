package com.vedangj044.frisson

import com.squareup.moshi.Json

data class UFOData(
    @Json(name = "country")
    val country: String,

    @Json(name = "id")
    val id: Int,

    @Json(name = "image")
    val image: String,

    @Json(name = "keywords")
    val keywords: String,

    @Json(name = "shape")
    val shape: String,

    @Json(name = "shape")
    val sightingDate: String,

    @Json(name = "state")
    val state: String
)