package com.example.series_collector.ui.home.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.BR

abstract class BindingViewHolder<VB : ViewDataBinding>(
    private val binding: VB,
) : RecyclerView.ViewHolder(binding.root) {

    protected var item: com.example.model.category.CategoryListItem? = null

    open fun bind(item: com.example.model.category.CategoryListItem) {
        this.item = item
        binding.setVariable(BR.item, this.item)
        binding.executePendingBindings()
    }
}