package com.mycorp.twitchapprxjava.screens.rating

import com.mycorp.twitchapprxjava.common.viewModel.BaseViewModel

class RatingViewModel : BaseViewModel() {

    fun updateRating(message: String) {
        showToast(message)
    }
}