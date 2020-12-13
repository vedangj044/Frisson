package com.vedangj044.frisson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class MainViewModel(private val apiService: APIService) : ViewModel() {
    val listData = Pager(PagingConfig(pageSize = 50)) {
        PostDataSource(apiService)
    }.flow.cachedIn(viewModelScope)
}