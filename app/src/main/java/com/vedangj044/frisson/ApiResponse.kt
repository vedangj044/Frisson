package com.vedangj044.frisson

import com.squareup.moshi.Json

data class ApiResponse(

    @Json(name = "has_more")
    val hasMore: Boolean,

    @Json(name = "items")
    val items: List<UFOData>,

    @Json(name = "results")
    val results: Int
)