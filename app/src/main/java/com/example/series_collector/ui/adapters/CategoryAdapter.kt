package com.example.series_collector.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.series_collector.data.Category
import com.example.series_collector.databinding.ListItemChapterBinding
import com.example.series_collector.utils.CATEGORY_FICTION
import com.example.series_collector.utils.CATEGORY_TRAVEL


class CategoryAdapter : ListAdapter<Category, RecyclerView.ViewHolder>(CategoryDiffCallback()) {

    lateinit private var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return CategoryViewHolder(
            ListItemChapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = getItem(position)
        (holder as CategoryViewHolder).bind(category, context)
    }

    class CategoryViewHolder(
        private val binding: ListItemChapterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category, context: Context) {
            binding.apply {
                category = item

                val adapter = SeriesAdapter()
                rvSeries.adapter = adapter
                if (category?.categoryId == CATEGORY_TRAVEL || category?.categoryId == CATEGORY_FICTION) {
                    rvSeries.layoutManager =
                        GridLayoutManager(context,2,RecyclerView.HORIZONTAL, false)
                }
                adapter.submitList(item.seriesList)

                executePendingBindings()
            }
        }
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.categoryId == newItem.categoryId
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}
