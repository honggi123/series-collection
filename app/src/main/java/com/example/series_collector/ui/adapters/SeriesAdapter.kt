package com.example.series_collector.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.data.room.entity.Series
import com.example.series_collector.databinding.ListItemHomeSeriesBinding
import com.example.series_collector.ui.home.HomeFragmentDirections


class SeriesAdapter : ListAdapter<Series, RecyclerView.ViewHolder>(SeriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

        init {
            binding.apply {
                imgSeriesThumbnail.clipToOutline = true
                setClickListener {
                    series?.let { series ->
                        navigateToDetail(series, it)
                    }
                }
            }
        }

        private fun navigateToDetail(
            series: Series,
            view: View
        ) {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(series.seriesId)
            view.findNavController().navigate(direction)
        }


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
        return oldItem.seriesId == newItem.seriesId
    }

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem == newItem
    }
}