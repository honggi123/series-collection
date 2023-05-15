package com.example.series_collector.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.data.api.SeriesVideo
import com.example.series_collector.databinding.ListItemSeriesVideoBinding


class SeriesVideoAdapter : PagingDataAdapter<SeriesVideo, SeriesVideoAdapter.SeriesVideoHolder>(SeriesVideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesVideoHolder {
        return SeriesVideoHolder(
            ListItemSeriesVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SeriesVideoHolder, position: Int) {
        val seriesVideo = getItem(position)
        if (seriesVideo != null) {
            holder.bind(seriesVideo)
        }
    }

    class SeriesVideoHolder(
        private val binding: ListItemSeriesVideoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.videoItemThumbnail.clipToOutline = true
        }

        fun bind(item: SeriesVideo) {
            binding.apply {
                seriesVideo = item
                executePendingBindings()
            }
        }
    }
}

private class SeriesVideoDiffCallback : DiffUtil.ItemCallback<SeriesVideo>() {
    override fun areItemsTheSame(oldItem: SeriesVideo, newItem: SeriesVideo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SeriesVideo, newItem: SeriesVideo): Boolean {
        return oldItem == newItem
    }
}

