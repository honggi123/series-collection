package com.example.series_collector.ui.adapters

import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.example.series_collector.R
import com.example.series_collector.utils.getGenreName

private const val SUB_DESCRIPTION_PREFIX = "#"

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

@BindingAdapter("genreHashtag")
fun bindGenreHashtag(textview: TextView, genre: Int) {
    val genreHashTaggedString = SUB_DESCRIPTION_PREFIX + getGenreName(genre)
    textview.text = genreHashTaggedString
}

@BindingAdapter("hashtag")
fun bindHashtag(textview: TextView, text: String?) {
    if (text != null) {
        val hashTaggedString = SUB_DESCRIPTION_PREFIX + text
        textview.text = hashTaggedString
    }
}

@BindingAdapter("hashtagTotalPage")
fun bindHashtagTotalPage(textview: TextView, count: Int) {
    val hashTaggedString = SUB_DESCRIPTION_PREFIX + count +"회차"
    textview.text = hashTaggedString
}