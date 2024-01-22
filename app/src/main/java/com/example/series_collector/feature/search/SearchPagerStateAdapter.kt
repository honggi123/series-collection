package com.example.series_collector.feature.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchPagerStateAdapter(
    fragment: Fragment,
    private val list: List<SearchFilterType>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return SearchFragment()
    }
}