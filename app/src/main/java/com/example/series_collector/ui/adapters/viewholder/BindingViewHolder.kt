package com.example.series_collector.ui.adapters.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.data.model.ListItem
import com.example.series_collector.BR

abstract class BindingViewHolder<VB : ViewDataBinding>(
    private val binding: VB,
) : RecyclerView.ViewHolder(binding.root) {

    protected var item: ListItem? = null

    open fun bind(item: ListItem) {
        this.item = item
        binding.setVariable(BR.item, this.item)
        binding.executePendingBindings()
    }
}