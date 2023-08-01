package com.example.series_collector.ui.adapters.viewholder

import androidx.core.view.setPadding
import com.example.series_collector.data.model.Horizontal
import com.example.series_collector.data.model.ListItem
import com.example.series_collector.data.model.ViewPager
import com.example.series_collector.databinding.ListItemAdViewPagerBinding
import com.example.series_collector.ui.adapters.HomeListAdapter

class AdViewPagerVIewHolder(
    binding: ListItemAdViewPagerBinding
) : BindingViewHolder<ListItemAdViewPagerBinding>(binding) {

    private var adapter = HomeListAdapter()

    init {
        binding.viewpager.adapter = adapter
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as ViewPager
        adapter.submitList(item.items)
    }

}