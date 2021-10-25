package com.mycorp.twitchapprxjava.presentation

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.mycorp.twitchapprxjava.R
import com.mycorp.twitchapprxjava.data.storage.model.GameData
import com.mycorp.twitchapprxjava.presentation.viewModel.BaseFragment
import com.mycorp.twitchapprxjava.presentation.viewModel.GameItemFragmentVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.mycorp.twitchapprxjava.databinding.FragmentGameItemBinding
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

const val SERIALIZED_GAME_KEY: String = "game"

class GameItemFragment : BaseFragment<GameItemFragmentVM>(R.layout.fragment_game_item) {
    override val viewModel: GameItemFragmentVM by viewModel()
    private val binding: FragmentGameItemBinding by viewBinding()
    private var gameData: GameData? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindVm()
    }

    private fun initViews() {
        with(binding) {
            gameData =
                Json.decodeFromString<GameData>(arguments?.getString(SERIALIZED_GAME_KEY)!!)
            binding.gameName.text = gameData?.name
            Glide.with(requireContext()).load(gameData?.logoUrl).into(image)
        }
    }

    override fun bindVm() {
        super.bindVm()
        viewModel.gameItemLiveData().observe(viewLifecycleOwner, {
            if (it.data != null) binding.followersCount.text =
                "followers count: ${it.data.size}"
        })
        viewModel.getFollowersListFromServer(gameData?.id.toString())
    }

}