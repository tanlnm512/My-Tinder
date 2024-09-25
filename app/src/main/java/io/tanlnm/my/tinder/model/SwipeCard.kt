package io.tanlnm.my.tinder.model

import androidx.annotation.Keep

@Keep
data class SwipeCard(
    val images: List<Int>,
    val name: String,
    val age: Int,
    val address: String,
    val distance: Int
)
