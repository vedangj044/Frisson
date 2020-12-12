package com.vedangj044.frisson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

class MainListAdapter : PagingDataAdapter<UFOData, MainListAdapter.ViewHolder>(DataDifferntiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var list_item_keywords = view.findViewById<MaterialTextView>(R.id.list_item_keywords)

        fun clear() {
            list_item_keywords.setText("")

        }

        fun bind(item: UFOData) {
            list_item_keywords.setText(item.keywords)
        }



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item= getItem(position);
        if(item == null) {
            holder.clear();
        } else {
            holder.bind(item)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_layout, parent, false)
        )
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