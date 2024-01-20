package com.example.series_collector.ui.home.adapter.viewholder

import android.graphics.Color
import android.graphics.PorterDuff
import com.example.series_collector.databinding.ListItemAdBinding

class AdViewHolder(
    binding: ListItemAdBinding
) : BindingViewHolder<ListItemAdBinding>(binding) {

    init {
        binding.imageView.setColorFilter(
            Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY
        )
        binding.order
    }
}
