package com.example.series_collector.ui.adapters.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.series_collector.data.model.ViewType
import com.example.series_collector.databinding.ListItemEmptyBinding
import com.google.android.material.tabs.TabLayout

object ViewHolderGenerator {

    val horizontalViewPool = RecyclerView.RecycledViewPool()

    fun get(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<*> {
        return when (viewType) {
            ViewType.VIEWPAGER.ordinal -> AdViewPagerVIewHolder(parent.toBinding())
            ViewType.HORIZONTAL.ordinal -> HorizontalViewHolder(
                parent.toBinding(),
                sharedPool = horizontalViewPool
            )

            ViewType.AD.ordinal -> AdViewHolder(parent.toBinding())
            ViewType.Series.ordinal -> SeriesViewHolder(parent.toBinding())
            else -> ItemViewHolder(parent.toBinding())
        }
    }

    class ItemViewHolder(binding: ListItemEmptyBinding) :
        BindingViewHolder<ListItemEmptyBinding>(binding)

    private inline fun <reified V : ViewBinding> ViewGroup.toBinding(): V {
        return V::class.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, LayoutInflater.from(context), this, false) as V
    }


}