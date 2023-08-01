package com.example.series_collector.ui.adapters.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.data.model.Horizontal
import com.example.series_collector.data.model.ListItem
import com.example.series_collector.databinding.ListItemHorizontalBinding
import com.example.series_collector.ui.adapters.HomeListAdapter

class HorizontalViewHolder(
    private val binding: ListItemHorizontalBinding,
    sharedPool: RecyclerView.RecycledViewPool
) : BindingViewHolder<ListItemHorizontalBinding>(binding) {

    private var adapter = HomeListAdapter()

    init {
        binding.rvSeries.adapter = adapter
        binding.rvSeries.setRecycledViewPool(sharedPool)
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as Horizontal

        binding.tvCategoryName.text = item.title
        adapter.submitList(item.items)
    }
}