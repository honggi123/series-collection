package com.example.series_collector.ui.adapters.viewholder

import com.example.series_collector.data.model.Horizontal
import com.example.series_collector.data.model.ListItem
import com.example.series_collector.databinding.ListItemHorizontalBinding
import com.example.series_collector.ui.adapters.HomeListAdapter

class HorizontalViewHolder(
    private val binding: ListItemHorizontalBinding
) : BindingViewHolder<ListItemHorizontalBinding>(binding) {

    private var adapter = HomeListAdapter()

    init {
        binding.rvSeries.adapter = adapter
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as Horizontal

        binding.tvCategoryName.text = item.title
        adapter.submitList(item.items)
    }
}