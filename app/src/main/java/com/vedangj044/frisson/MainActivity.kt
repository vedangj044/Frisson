package com.vedangj044.frisson

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Frisson)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupList()
        setupView()

        backdropBehavior = findViewById<CoordinatorLayout>(R.id.frontLayout).findBehavior()
        with(backdropBehavior) {
            attachBackLayout(R.id.backLayout)
            setOpenedIcon(R.drawable.ic_backdrop_close)
            setClosedIcon(R.drawable.ic_backdrop_menu)
        }
    }

    private fun setupView() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                mainListAdapter.submitData(it)
            }
        }

        resultCountTextView = findViewById(R.id.result_count_text_view)
        viewModel.count.observe(this, Observer {
                value -> resultCountTextView.text = value.toString()
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
                MainViewModelFactory(APIService.getApiService())
            )[MainViewModel::class.java]
    }

    private fun <T : CoordinatorLayout.Behavior<*>> View.findBehavior(): T = layoutParams.run {
        if (this !is CoordinatorLayout.LayoutParams) throw IllegalArgumentException("View's layout params should be CoordinatorLayout.LayoutParams")

        (layoutParams as CoordinatorLayout.LayoutParams).behavior as? T
            ?: throw IllegalArgumentException("Layout's behavior is not current behavior")
    }
}