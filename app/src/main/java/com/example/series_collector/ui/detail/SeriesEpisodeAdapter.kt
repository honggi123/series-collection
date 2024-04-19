package com.example.series_collector.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.model.episode.Episode
import com.example.series_collector.databinding.ListItemSeriesEpisodeBinding

class SeriesEpisodeAdapter : PagingDataAdapter<Episode, SeriesEpisodeAdapter.SeriesEpisodeHolder>(
    SeriesEposideDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SeriesEpisodeHolder {
        return SeriesEpisodeHolder(
            ListItemSeriesEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: SeriesEpisodeHolder,
        position: Int,
    ) {
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

        fun bind(item: Episode) {
            binding.apply {
                episode = item
                executePendingBindings()
            }
        }
    }
}

private class SeriesEposideDiffCallback : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }
}