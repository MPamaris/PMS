package com.stellantis.crf.pms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    lateinit var binding: FragmentNotificationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationsBinding.inflate(layoutInflater)
        val view = binding.root

        binding.idIncludeToolbarNotifications.idButtonBackToolbarNotifications.setOnClickListener {
            goToHomePage()
        }

        return view
    }

    private fun goToHomePage() {
        findNavController().navigate(R.id.action_notificationsFragment_to_homeFragment)
    }
}