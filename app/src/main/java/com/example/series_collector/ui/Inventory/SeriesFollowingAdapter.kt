package com.example.series_collector.ui.Inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.model.series.Series
import com.example.series_collector.databinding.ListItemInventorySeriesBinding

class SeriesFollowingAdapter(
    private val inventoryItemCallback: InventoryItemCallback
) : ListAdapter<Series, RecyclerView.ViewHolder>(SeriesFollowedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SeriesFollowingViewHolder(
            ListItemInventorySeriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            inventoryItemCallback
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val series = getItem(position)
        (holder as SeriesFollowingViewHolder).bind(series)
    }

    class SeriesFollowingViewHolder(
        private val binding: ListItemInventorySeriesBinding,
        private val inventoryItemCallback: InventoryItemCallback
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                seriesFollowedThumbnail.setOnClickListener {
                    series?.let { series -> openDetail(series) }
                }
                btnDelete.setOnClickListener {
                    series?.let { series -> deleteSeries(series) }
                }
            }
        }

        private fun openDetail(series: Series) {
            inventoryItemCallback.openSeriesDetail(seriesId = series.id)
        }

        private fun deleteSeries(series: Series) {
            inventoryItemCallback.deleteItem(series.id)
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
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem == newItem
    }
}
