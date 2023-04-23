package com.example.series_collector.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.series_collector.data.Category
import com.example.series_collector.databinding.ListItemCategoryBinding
import com.example.series_collector.utils.CATEGORY_FICTION
import com.example.series_collector.utils.CATEGORY_TRAVEL


class CategoryAdapter : ListAdapter<Category, RecyclerView.ViewHolder>(CategoryDiffCallback()) {

    lateinit private var context: Context
    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return CategoryViewHolder(
            ListItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = getItem(position)
        (holder as CategoryViewHolder).bind(item = category, context = context, sharedPool = viewPool)
    }

    class CategoryViewHolder(
        private val binding: ListItemCategoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category, context: Context, sharedPool: RecyclerView.RecycledViewPool) {
            binding.apply {
                category = item

                val adapter = SeriesAdapter()

                (rvSeries.layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true

                rvSeries.adapter = adapter
                if (category?.categoryId == CATEGORY_TRAVEL || category?.categoryId == CATEGORY_FICTION) {
                    rvSeries.layoutManager =
                        GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
                }

                rvSeries.setRecycledViewPool(sharedPool)

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
