package com.example.series_collector.ui.home.adapter.viewholder

import android.view.View
import androidx.navigation.findNavController
import com.example.series_collector.data.model.series.Series
import com.example.series_collector.databinding.ListItemHomeSeriesBinding
import com.example.series_collector.ui.home.HomeFragmentDirections

class SeriesViewHolder(
    private val binding: ListItemHomeSeriesBinding
) : BindingViewHolder<ListItemHomeSeriesBinding>(binding) {

    init {
        binding.apply {
            imgSeriesThumbnail.clipToOutline = true
            setClickListener {
                item?.let { series ->
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
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(series.id)
        view.findNavController().navigate(direction)
    }

}