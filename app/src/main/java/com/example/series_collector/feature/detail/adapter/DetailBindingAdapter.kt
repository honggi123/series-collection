package com.example.series_collector.feature.detail.adapter

import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.example.series_collector.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter("isFollowed")
fun bindisFollowed(button: AppCompatButton, isFollowed: Boolean) {
    if (isFollowed) {
        button.isSelected = false
        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0)
    } else {
        button.isSelected = true
        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_24, 0, 0, 0)
    }
}

@BindingAdapter("toast")
fun bindToast(view: View, msg: String?) {
    if (!msg.isNullOrEmpty()) {
        Toast.makeText(view.context, msg, Toast.LENGTH_SHORT).show()
    }
}

@BindingAdapter("tags")
fun bindTags(chipGroup: ChipGroup, tags: List<com.example.model.common.Tag>?) {
    tags?.forEach { tag ->
        val tagView: Chip = Chip(chipGroup.context).apply {
            text = if (tag.type == com.example.model.common.TagType.TOTAL_PAGE) "${tag.name}화" else tag.name
            isCheckable = false
            isCloseIconVisible = false
            setChipBackgroundColorResource(R.color.sc_gray)
        }
        chipGroup.addView(tagView)
    }
}