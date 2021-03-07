package com.vedangj044.frisson

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.semper_viventem.backdrop.BackdropBehavior


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var themeToggleButton: ImageView
    private val viewModel by viewModels<MainViewModel>()
    private var listener: OnBottomSheetCallbacks? = null
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Frisson)
        setContentView(R.layout.activity_main)


        observeTheme()

        themeToggleButton = findViewById(R.id.theme_toggle_button)
        themeToggleButton.setOnClickListener {
            if (mBottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
                openBottomSheet()
                viewModel.toggleTheme(true)
            }
            else{
                viewModel.toggleTheme()
            }
        }

        supportActionBar?.elevation = 0f

        configureBackdrop()
    }

    private fun observeTheme(){
        viewModel.themeMode.observe(this, Observer {
            value -> when(value) {
                ThemeDataSource.lightMode -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    themeToggleButton.setImageResource(R.drawable.ic_dark_mode)
                }
                ThemeDataSource.darkMode -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    themeToggleButton.setImageResource(R.drawable.ic_light_mode)
                }
            }
        })
    }

    fun setOnBottomSheetCallbacks(onBottomSheetCallbacks: OnBottomSheetCallbacks) {
        this.listener = onBottomSheetCallbacks
    }

    fun closeBottomSheet() {
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun openBottomSheet() {
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun configureBackdrop() {
        val fragment = supportFragmentManager.findFragmentById(R.id.filter_fragment)

        fragment?.view?.let {
            BottomSheetBehavior.from(it).let { bs ->
                bs.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        listener?.onStateChanged(bottomSheet, newState)
                    }
                })

                bs.state = BottomSheetBehavior.STATE_EXPANDED
                mBottomSheetBehavior = bs
            }
        }
    }
}