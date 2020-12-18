package com.vedangj044.frisson

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(apiService: APIService) : ViewModel() {

    private val pagingDataSource: PostDataSource = PostDataSource(apiService)
    var count: MutableLiveData<Int> = MutableLiveData()

    val listData = Pager(PagingConfig(pageSize = 50)) {
        pagingDataSource
    }.flow.cachedIn(viewModelScope)

    init {

        /*
        The result count is passed from the repository class as a stateFlow,
        Further it is converted to live data and send to the ui.
        The stateFlow is not collected on the ui as it is not lifecycle aware and receives
        update even after the ui is not visible.
        reference : https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
         */

        viewModelScope.launch {
            pagingDataSource.resultCount.collect{
                value ->  count.value = value
            }
        }
    }
}