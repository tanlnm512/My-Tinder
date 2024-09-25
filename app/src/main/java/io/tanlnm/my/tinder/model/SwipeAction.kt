package io.tanlnm.my.tinder.model

import androidx.annotation.Keep

@Keep
data class SwipeAction(
    val top: SwipeCard,
    val bottom: SwipeCard,
)
