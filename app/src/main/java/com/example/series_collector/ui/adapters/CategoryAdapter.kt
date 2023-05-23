package com.example.series_collector.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.series_collector.data.entitiy.Category
import com.example.series_collector.databinding.ListItemCategoryBinding


class CategoryAdapter : ListAdapter<Category, RecyclerView.ViewHolder>(CategoryDiffCallback()) {

    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
        (holder as CategoryViewHolder).bind(
            item = category,
            sharedPool = viewPool
        )
    }

    class CategoryViewHolder(
        private val binding: ListItemCategoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var snapHelper: SnapHelper = LinearSnapHelper()
        private var adapter: SeriesAdapter = SeriesAdapter()

        fun bind(item: Category, sharedPool: RecyclerView.RecycledViewPool) {
            binding.apply {
                category = item
                (rvSeries.layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true
                rvSeries.adapter = adapter
                rvSeries.setRecycledViewPool(sharedPool)
                rvSeries.setNestedScrollingEnabled(false)

                snapHelper.attachToRecyclerView(rvSeries)
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
