package com.example.series_collector.ui.home.adapter.viewholder

import com.example.series_collector.model.category.CategoryListItem
import com.example.series_collector.model.category.ViewPager
import com.example.series_collector.databinding.ListItemAdViewPagerBinding
import com.example.series_collector.ui.home.adapter.HomeListAdapter

class AdViewPagerViewHolder(
    binding: ListItemAdViewPagerBinding
) : BindingViewHolder<ListItemAdViewPagerBinding>(binding) {

    private var adapter = HomeListAdapter()

    init {
        binding.viewpager.adapter = adapter
    }

    // TODO binding page num, total page
    override fun bind(item: CategoryListItem) {
        super.bind(item)
        item as ViewPager
        adapter.submitList(item.items)
    }

}