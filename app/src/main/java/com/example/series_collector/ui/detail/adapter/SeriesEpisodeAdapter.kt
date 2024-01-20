package com.example.series_collector.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.data.source.remote.api.model.SeriesEpisode
import com.example.series_collector.databinding.ListItemSeriesEpisodeBinding

class SeriesEpisodeAdapter : PagingDataAdapter<SeriesEpisode, SeriesEpisodeAdapter.SeriesEpisodeHolder>(
    SeriesEposideDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesEpisodeHolder {
        return SeriesEpisodeHolder(
            ListItemSeriesEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SeriesEpisodeHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class SeriesEpisodeHolder(
        private val binding: ListItemSeriesEpisodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgSeriesThumbnail.clipToOutline = true
        }

        fun bind(item: SeriesEpisode) {
            binding.apply {
                seriesEpisode = item
                executePendingBindings()
            }
        }
    }
}

private class SeriesEposideDiffCallback : DiffUtil.ItemCallback<SeriesEpisode>() {
    override fun areItemsTheSame(oldItem: SeriesEpisode, newItem: SeriesEpisode): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SeriesEpisode, newItem: SeriesEpisode): Boolean {
        return oldItem == newItem
    }
}