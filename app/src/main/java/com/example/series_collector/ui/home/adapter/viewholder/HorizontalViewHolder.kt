package com.example.series_collector.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.model.category.Horizontal
import com.example.series_collector.model.category.CategoryListItem
import com.example.series_collector.databinding.ListItemHorizontalBinding
import com.example.series_collector.ui.home.adapter.HomeListAdapter

class HorizontalViewHolder(
    private val binding: ListItemHorizontalBinding,
    sharedPool: RecyclerView.RecycledViewPool
) : BindingViewHolder<ListItemHorizontalBinding>(binding) {

    private var adapter = HomeListAdapter()

    init {
        binding.rvSeries.adapter = adapter
        binding.rvSeries.setRecycledViewPool(sharedPool)
    }

    override fun bind(item: CategoryListItem) {
        super.bind(item)
        item as Horizontal

        binding.tvCategoryName.text = item.title
        adapter.submitList(item.items)
    }
}