package io.tanlnm.my.tinder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.tanlnm.my.tinder.model.SwipeAction
import io.tanlnm.my.tinder.model.SwipeCard

class HomeViewModel : ViewModel() {

    val swipeActionStream = MutableLiveData<SwipeAction>()

    private val mockupData = listOf(
        SwipeCard(
            listOf(R.drawable.img_suboi_1, R.drawable.img_suboi_2),
            "Suboi", 34,
            "Sai Gon", 23
        ),
        SwipeCard(
            listOf(
                R.drawable.img_hongocha_1,
                R.drawable.img_hongocha_2,
                R.drawable.img_hongocha_3
            ),
            "Hồ Ngọc Hà", 39,
            "Hà Nội", 300
        ),
        SwipeCard(
            listOf(
                R.drawable.img_vuthaomy_1,
                R.drawable.img_vuthaomy_2,
                R.drawable.img_vuthaomy_3,
            ),
            "Vũ Thảo My", 27,
            "Ho Chi Minh", 12
        )
    )

    private var curIndex = 0

    init {
        updateSwipeAction()
    }

    private fun getTopCard(): SwipeCard {
        return mockupData[curIndex % mockupData.size]
    }

    private fun getBottomCard(): SwipeCard {
        return mockupData.get((curIndex + 1) % mockupData.size)
    }

    fun swipe() {
        curIndex += 1
        updateSwipeAction()
    }

    private fun updateSwipeAction() {
        swipeActionStream.value = SwipeAction(getTopCard(), getBottomCard())
    }
}