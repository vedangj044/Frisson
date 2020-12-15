package com.vedangj044.frisson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vedangj044.frisson.databinding.ListItemLayoutBinding

class MainListAdapter : PagingDataAdapter<UFOData, MainListAdapter.ViewHolder>(DataDifferntiator) {

    class ViewHolder(private val view: ListItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(ufoData: UFOData) {
            view.ufoData = ufoData
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    object DataDifferntiator : DiffUtil.ItemCallback<UFOData>() {

        override fun areItemsTheSame(oldItem: UFOData, newItem: UFOData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UFOData, newItem: UFOData): Boolean {
            return oldItem == newItem
        }
    }

}