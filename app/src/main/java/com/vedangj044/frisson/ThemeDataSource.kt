package com.vedangj044.frisson

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

// Reference https://proandroiddev.com/using-livedata-flow-in-mvvm-part-i-a98fe06077a0
@Singleton
class ThemeDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {

        const val darkMode: Int = 1
        const val lightMode: Int = 0

    }


    private val dataStoreKey = preferencesKey<Int>("mode")
    private val _themeMode = MutableStateFlow(lightMode)
    val themeMode: StateFlow<Int> get() = _themeMode

    init {
        GlobalScope.launch {
            getTheme()
        }
    }

    private suspend fun getTheme() {
        val preference = dataStore.data.first()
        _themeMode.value = preference[dataStoreKey] ?: lightMode
    }

    suspend fun toggleTheme() {
        dataStore.edit {
            theme -> theme[dataStoreKey] = if (_themeMode.value == darkMode) lightMode else darkMode
        }
        getTheme()
    }

}