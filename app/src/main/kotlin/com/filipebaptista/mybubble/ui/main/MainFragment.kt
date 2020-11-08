package com.filipebaptista.mybubble.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.filipebaptista.mybubble.BubbleApplication
import com.filipebaptista.mybubble.R
import com.filipebaptista.mybubble.databinding.MainFragmentBinding
import com.filipebaptista.mybubble.extension.viewBindings

class MainFragment : Fragment(R.layout.main_fragment) {
    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels {
        (requireActivity().applicationContext as BubbleApplication).bubbleViewModelFactory
    }
    private val binding by viewBindings(MainFragmentBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnShowBubble.setOnClickListener {
            viewModel.showBubbleNotification()
        }
    }
}
