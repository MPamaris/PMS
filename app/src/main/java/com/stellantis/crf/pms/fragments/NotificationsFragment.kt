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
    //private val argsCoaching: DetailsPageFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationsBinding.inflate(layoutInflater)

        val notifyReceived = args.isNotify.isNotify
        val vehicleReceived = args.isNotify.vehicleSelected
        //val argumentFromCoaching = argsCoaching.argumentsFromCoaching.argumentsCoachingAdvise

        val bodyNotifications = binding.idIncludeBodyNotifications.root
        val bodyNotificationsMaintenanceToBePlaned = binding.idIncludeBodyNotificationsCriticalMaintenance.root

        //val bodyNotificationsTile = binding.idIncludeBodyNotifications.idCardviewBodyNotifications
        val bodyNotificationsTile = binding.idIncludeBodyNotifications.idTileNotification.root
        val bodyNotificationsMaintenanceToBePlanedTile = binding.idIncludeBodyNotificationsCriticalMaintenance.idCardviewBodyNotifications

        /*when (notifyReceived) {
            "IS_NOTIFICATION" -> bodyNotifications.visibility = View.VISIBLE
            "IS_NOT_NOTIFICATION" -> bodyNotificationsMaintenanceToBePlaned.visibility = View.VISIBLE
        }*/
        when (notifyReceived) {
            "IS_NOTIFICATION" -> bodyNotifications.visibility = View.VISIBLE
            "IS_NOT_NOTIFICATION" -> bodyNotificationsMaintenanceToBePlaned.visibility = View.VISIBLE
        }

        //Toast.makeText(activity, "!! --> " + argumentFromCoaching, Toast.LENGTH_SHORT).show()

        bodyNotifications.setOnClickListener {
            goToDetailsPage()
        }

        bodyNotificationsTile.setOnClickListener {
            goToDetailsPage()
        }
        bodyNotificationsMaintenanceToBePlanedTile.setOnClickListener {
            Toast.makeText(activity, "Critical", Toast.LENGTH_SHORT).show()
        }

        val view = binding.root

        binding.idIncludeToolbarNotifications.idButtonBackToolbarNotifications.setOnClickListener {
            goBack()
        }

        return view
    }

    private fun goToDetailsPage() {
        findNavController().navigate(R.id.action_notificationsFragment_to_detailsPageFragment)
    }

    private fun goBack() {
        findNavController().navigate(R.id.action_notificationsFragment_to_homeFragment)
    }
}