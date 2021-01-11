package com.vedangj044.frisson

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//class MainViewModelFactory(private val apiService: APIService, private val dataStore: DataStore<Preferences>) : ViewModelProvider.Factory {
//
////    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
////        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
////            return MainViewModel(apiService, dataStore) as T
////        }
////        throw IllegalArgumentException("Unknown ViewModel class")
////    }
//}