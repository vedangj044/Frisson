package com.vedangj044.frisson

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.semper_viventem.backdrop.BackdropBehavior


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mainListAdapter: MainListAdapter
    private lateinit var recyclerView:RecyclerView
    private lateinit var backdropBehavior: BackdropBehavior
    private lateinit var resultCountTextView: TextView
    private lateinit var themeToggleButton: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Frisson)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE

        setupViewModel()
        setupList()
        setupView()
        observeTheme()

        backdropBehavior = findViewById<CoordinatorLayout>(R.id.frontLayout).findBehavior()
        with(backdropBehavior) {
            attachBackLayout(R.id.backLayout)
            setOpenedIcon(R.drawable.ic_backdrop_close)
            setClosedIcon(R.drawable.ic_backdrop_menu)
        }

        themeToggleButton = findViewById(R.id.theme_toggle_button)
        themeToggleButton.setOnClickListener {
            viewModel.toggleTheme()
        }

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

    private fun setupView() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                mainListAdapter.submitData(it)
            }
        }

        resultCountTextView = findViewById(R.id.result_count_text_view)
        viewModel.count.observe(this, Observer {
                value -> resultCountTextView.text =
            String.format(resources.getString(R.string.results_count),
                getApproximateValue(value))

            if(value != 0) progressBar.visibility = View.GONE
        })
    }

    private fun setupList() {
        mainListAdapter = MainListAdapter()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mainListAdapter
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(APIService.getApiService(),
                    createDataStore(name = "themeMode"))
            )[MainViewModel::class.java]
    }

    private fun <T : CoordinatorLayout.Behavior<*>> View.findBehavior(): T = layoutParams.run {
        if (this !is CoordinatorLayout.LayoutParams) throw IllegalArgumentException("View's layout params should be CoordinatorLayout.LayoutParams")

        (layoutParams as CoordinatorLayout.LayoutParams).behavior as? T
            ?: throw IllegalArgumentException("Layout's behavior is not current behavior")
    }
}