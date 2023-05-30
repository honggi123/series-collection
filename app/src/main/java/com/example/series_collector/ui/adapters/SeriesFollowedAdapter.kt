package com.example.series_collector.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.data.room.entity.SeriesEntity
import com.example.series_collector.databinding.ListItemSeriesFollowedBinding
import com.example.series_collector.ui.Inventory.InventoryItemCallback

class SeriesFollowedAdapter(
    private val inventoryItemCallback: InventoryItemCallback
) : ListAdapter<SeriesEntity, RecyclerView.ViewHolder>(SeriesFollowedDiffCallback()) {

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

        private fun deleteSeries(series: SeriesEntity) {
            inventoryItemCallback.deleteItem(series.seriesId)
        }


        fun bind(item: SeriesEntity) {
            binding.apply {
                binding.series = item
                executePendingBindings()
            }
        }
    }
}

private class SeriesFollowedDiffCallback : DiffUtil.ItemCallback<SeriesEntity>() {

    override fun areItemsTheSame(oldItem: SeriesEntity, newItem: SeriesEntity): Boolean {
        return oldItem.seriesId == newItem.seriesId
    }

    override fun areContentsTheSame(oldItem: SeriesEntity, newItem: SeriesEntity): Boolean {
        return oldItem == newItem
    }
}
