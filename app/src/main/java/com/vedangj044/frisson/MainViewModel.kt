package com.vedangj044.frisson

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val pagingDataSource: PostDataSource, private val themeDataSource: ThemeDataSource) : ViewModel() {

    /*
    The variables are passed from the repository class as a stateFlow,
    Further it is converted to live data and send to the ui.
    The stateFlow is not collected on the ui as it is not lifecycle aware and receives
    update even after the ui is not visible.
    reference : https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
     */


    var count: LiveData<Int> = pagingDataSource.resultCount.asLiveData(viewModelScope.coroutineContext)
    var themeMode: LiveData<Int> = themeDataSource.themeMode.asLiveData(viewModelScope.coroutineContext)

    val listData = Pager(PagingConfig(pageSize = 50)) {
        pagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun toggleTheme() {
        viewModelScope.launch {
            themeDataSource.toggleTheme()
        }
    }
}