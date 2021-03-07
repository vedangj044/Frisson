package com.vedangj044.frisson

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainListFragment: BottomSheetDialogFragment(), OnBottomSheetCallbacks {

    private lateinit var mainListAdapter: MainListAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var resultCountTextView: TextView
    private lateinit var bottomSheetNavigation: AppCompatImageView
    private val viewModel: MainViewModel by viewModels()

    private var currentState: Int = BottomSheetBehavior.STATE_EXPANDED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).setOnBottomSheetCallbacks(this)

        return inflater.inflate(R.layout.layout_main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultCountTextView = view.findViewById(R.id.result_count_text_view)
        bottomSheetNavigation = view.findViewById(R.id.bottom_sheet_navigation)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progress_bar)

        bottomSheetNavigation.setOnClickListener {
            if (currentState == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetNavigation.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                (activity as MainActivity).closeBottomSheet()

            } else  {
                bottomSheetNavigation.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                (activity as MainActivity).openBottomSheet()
            }
        }

        setupList()
        setupView()

    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        currentState = newState
    }

    private fun setupView() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                mainListAdapter.submitData(it)
            }
        }

        viewModel.count.observe(this, Observer {
                value -> resultCountTextView.text =
            String.format(resources.getString(R.string.results_count),
                getApproximateValue(value))

            if(value != 0) progressBar.visibility = View.GONE
        })
    }

    private fun setupList() {
        mainListAdapter = MainListAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mainListAdapter
        }
    }

}