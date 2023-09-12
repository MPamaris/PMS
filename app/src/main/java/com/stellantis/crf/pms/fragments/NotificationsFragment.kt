package com.stellantis.crf.pms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    lateinit var binding: FragmentNotificationsBinding
    private val args: NotificationsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationsBinding.inflate(layoutInflater)

        val notifyReceived = args.isNotify.isNotify

        //Toast.makeText(activity, notifyReceived, Toast.LENGTH_SHORT).show()
        val bodyNotifications = binding.idIncludeBodyNotifications.idCardviewBodyNotifications

        when (notifyReceived) {
            "IS_NOTIFICATION" -> bodyNotifications.visibility = View.VISIBLE
            "IS_NOT_NOTIFICATION" -> bodyNotifications.visibility = View.GONE
        }

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