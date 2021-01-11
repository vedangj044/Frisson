package com.vedangj044.frisson

import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: APIService): ApiHelper {
    override suspend fun getListData(pageNumber: Int): Response<ApiResponse>  = apiService.getListData(pageNumber)
}