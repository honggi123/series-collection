package com.example.series_collector.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.series_collector.databinding.FragmentInventoryBinding
import com.example.series_collector.databinding.FragmentSearchBinding
import com.example.series_collector.ui.Inventory.InventoryItemCallback
import com.example.series_collector.ui.Inventory.InventoryViewModel
import com.example.series_collector.ui.adapters.SeriesFollowedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment() : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

}


