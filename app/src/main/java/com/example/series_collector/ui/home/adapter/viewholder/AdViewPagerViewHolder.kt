package com.example.series_collector.ui.home.adapter.viewholder

import com.example.model.category.CategoryListItem
import com.example.model.category.ViewPager
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
    override fun bind(item: com.example.model.category.CategoryListItem) {
        super.bind(item)
        item as com.example.model.category.ViewPager
        adapter.submitList(item.items)
    }

}