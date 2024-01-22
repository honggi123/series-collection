package com.example.series_collector.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.model.episode.Episode
import com.example.series_collector.databinding.ListItemSeriesEpisodeBinding

class SeriesEpisodeAdapter : PagingDataAdapter<com.example.model.episode.Episode, SeriesEpisodeAdapter.SeriesEpisodeHolder>(
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

        fun bind(item: com.example.model.episode.Episode) {
            binding.apply {
                episode = item
                executePendingBindings()
            }
        }
    }
}

private class SeriesEposideDiffCallback : DiffUtil.ItemCallback<com.example.model.episode.Episode>() {
    override fun areItemsTheSame(oldItem: com.example.model.episode.Episode, newItem: com.example.model.episode.Episode): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: com.example.model.episode.Episode, newItem: com.example.model.episode.Episode): Boolean {
        return oldItem == newItem
    }
}