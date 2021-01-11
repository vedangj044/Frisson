package com.vedangj044.frisson

import retrofit2.Response
import retrofit2.http.Query

interface ApiHelper {
    suspend fun getListData(@Query("page") pageNumber: Int): Response<ApiResponse>
}