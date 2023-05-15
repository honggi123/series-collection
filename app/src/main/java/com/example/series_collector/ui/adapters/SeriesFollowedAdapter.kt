package com.example.series_collector.ui.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.data.Series
import com.example.series_collector.databinding.ListItemSeriesFollowedBinding
import com.example.series_collector.ui.Inventory.InventoryItemCallback
import com.example.series_collector.ui.home.HomeFragmentDirections

class SeriesFollowedAdapter(
    private val inventoryItemCallback: InventoryItemCallback
) : ListAdapter<Series, RecyclerView.ViewHolder>(SeriesFollowedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SeriesFollowedViewHolder(
            ListItemSeriesFollowedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            inventoryItemCallback
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val series = getItem(position)
        (holder as SeriesFollowedViewHolder).bind(series)
    }

    class SeriesFollowedViewHolder(
        private val binding: ListItemSeriesFollowedBinding,
        private val inventoryItemCallback: InventoryItemCallback
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnDelete.setOnClickListener {
                binding.series?.let { series ->
                    deleteSeries(series)
                }
            }
        }

        private fun deleteSeries(series: Series) {
            inventoryItemCallback.deleteItem(series.seriesId)
        }


        fun bind(item: Series) {
            binding.apply {
                binding.series = item
                executePendingBindings()
            }
        }
    }
}

private class SeriesFollowedDiffCallback : DiffUtil.ItemCallback<Series>() {

    override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.seriesId == newItem.seriesId
    }

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem == newItem
    }
}
