package com.example.series_collector.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.model.series.Series
import com.example.series_collector.databinding.ListItemSearchSeriesBinding

class SearchSeriesAdapter : ListAdapter<Series, RecyclerView.ViewHolder>(SearchSeriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchSeriesViewHolder(
            ListItemSearchSeriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val series = getItem(position)
        (holder as SearchSeriesViewHolder).bind(series)
    }

    class SearchSeriesViewHolder(
        private val binding: ListItemSearchSeriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                imgSeriesThumbnail.clipToOutline = true
            }
        }

        fun bind(item: com.example.model.series.Series) {
            binding.apply {
                binding.series = item
                executePendingBindings()
            }
        }
    }
}

private class SearchSeriesDiffCallback : DiffUtil.ItemCallback<com.example.model.series.Series>() {

    override fun areItemsTheSame(oldItem: com.example.model.series.Series, newItem: com.example.model.series.Series): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: com.example.model.series.Series, newItem: com.example.model.series.Series): Boolean {
        return oldItem == newItem
    }
}