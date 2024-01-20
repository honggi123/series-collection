package com.example.series_collector.ui.Inventory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.series_collector.databinding.FragmentInventoryBinding
import com.example.series_collector.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InventoryFragment() : Fragment(), InventoryItemCallback {
    private val adapter = SeriesFollowingAdapter(this)
    private val inventoryViewModel: InventoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentInventoryBinding.inflate(inflater, container, false)
        binding.apply {
            rvSeriesFollowed.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
        }
        subscribe()

        return binding.root
    }

    private fun subscribe() {
        lifecycleScope.launch {
            inventoryViewModel.mySeriesList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun openSeriesDetail(seriesId: String) {
        val direction = InventoryFragmentDirections.actionInventoryFragmentToDetailFragment(seriesId)
        view?.findNavController()?.navigate(direction)
    }

    override fun deleteItem(seriesId: String) {
        inventoryViewModel.unFollowSeries(seriesId)
    }

}

interface InventoryItemCallback {

    fun openSeriesDetail(seriesId: String)

    fun deleteItem(seriesId: String)
}

