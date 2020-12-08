package com.vedangj044.frisson

import androidx.paging.PagingSource
import java.lang.Exception

// Reference Link : https://blog.mindorks.com/paging-3-tutorial

class PostDataSource(private val apiService: APIService): PagingSource<Int, UFOData>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UFOData> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getListData(currentLoadingPageKey)
            val responseData = mutableListOf<UFOData>()
            val data = response.body()?.items ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = if (response.body()?.hasMore == true) currentLoadingPageKey.plus(1) else null
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

}