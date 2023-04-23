package com.example.series_collector.ui.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.data.Category
import com.example.series_collector.data.Series
import com.example.series_collector.databinding.ListItemHomeSeriesBinding


class SeriesAdapter : ListAdapter<Series, RecyclerView.ViewHolder>(SeriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      Log.e("onCreateViewHolder","OncreateViewHolder")
        return SeriesViewHolder(
            ListItemHomeSeriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val series = getItem(position)
        (holder as SeriesViewHolder).bind(series)
    }

    class SeriesViewHolder(
        private val binding: ListItemHomeSeriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Series) {
            binding.apply {
                binding.series = item
                executePendingBindings()
            }
        }
    }
}

private class SeriesDiffCallback : DiffUtil.ItemCallback<Series>() {

    override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.SeriesId == newItem.SeriesId
    }

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem == newItem
    }
}
