package com.vedangj044.frisson

import androidx.paging.PagingSource
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

// Reference Link : https://blog.mindorks.com/paging-3-tutorial
@Singleton
class PostDataSource @Inject constructor(private val apiHelper: ApiHelper): PagingSource<Int, UFOData>(){

    private val _resultCount = MutableStateFlow(0)
    val resultCount: StateFlow<Int> get() = _resultCount


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UFOData> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiHelper.getListData(currentLoadingPageKey)
            val responseData = mutableListOf<UFOData>()
            val data = response.body()?.items ?: emptyList()
            _resultCount.value = response.body()?.results!!
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