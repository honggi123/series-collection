package com.example.series_collector.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.series_collector.ui.search.SearchFilterType
import com.example.series_collector.ui.search.SearchFragment

class SearchPagerStateAdapter(
    fragment: Fragment,
    private val list: List<SearchFilterType>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment =
        SearchFragment.newInstance(position)
}