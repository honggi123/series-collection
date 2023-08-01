package com.example.series_collector.ui.adapters.viewholder

import android.graphics.Color
import android.graphics.PorterDuff
import com.example.series_collector.data.model.ListItem
import com.example.series_collector.databinding.ListItemAdBinding

class AdViewHolder(
    binding: ListItemAdBinding
) : BindingViewHolder<ListItemAdBinding>(binding) {

    init {
        binding.imageView.setColorFilter(
            Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY
        )
    }
}
