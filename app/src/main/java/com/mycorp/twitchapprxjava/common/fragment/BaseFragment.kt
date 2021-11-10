package com.mycorp.twitchapprxjava.common.fragment

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mycorp.twitchapprxjava.common.viewModel.BaseViewModel

@SuppressLint("ResourceType")
abstract class BaseFragment<VM : BaseViewModel>(layoutId: Int) : Fragment(layoutId), FragmentScene {
    abstract val viewModel: VM

    override val self: Fragment
        get() = this

    open fun bindVm() {
        viewModel.showToast.observe(this, {
            if (it == null) return@observe
            val (text, length) = it

            Toast.makeText(requireContext(), text, length).show()
        })
    }

}


