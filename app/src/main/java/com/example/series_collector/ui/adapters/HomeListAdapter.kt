package com.example.series_collector.ui.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.series_collector.data.model.ListItem
import com.example.series_collector.ui.adapters.viewholder.BindingViewHolder
import com.example.series_collector.ui.adapters.viewholder.ViewHolderGenerator

class HomeListAdapter : ListAdapter<ListItem, BindingViewHolder<*>>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item?.viewType?.ordinal ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return ViewHolderGenerator.get(parent, viewType)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

}

class DiffCallback<T : ListItem> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) =
        oldItem.viewType == newItem.viewType && oldItem.getKey() == newItem.getKey()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}