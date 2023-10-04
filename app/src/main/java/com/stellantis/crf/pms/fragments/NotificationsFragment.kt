package com.stellantis.crf.pms.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stellantis.crf.pms.PmsRepository
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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
        //val bodyNotificationsMaintenanceToBePlaned = binding.idIncludeBodyNotificationsCriticalMaintenance.root

        //val bodyNotificationsTile = binding.idIncludeBodyNotifications.idCardviewBodyNotifications
        val bodyNotificationsTile = binding.idIncludeBodyNotifications.idTileNotification.root
        //val bodyNotificationsMaintenanceToBePlanedTile = binding.idIncludeBodyNotificationsCriticalMaintenance.idCardviewBodyNotifications

        when (notifyReceived) {
            "IS_NOTIFICATION_NEW" -> binding.idIncludeBodyNotifications.idTileNotificationToBePlanned.root.visibility = View.VISIBLE
            "IS_NOT_NOTIFICATION_NEW" -> binding.idIncludeBodyNotifications.idTileNotification.root.visibility = View.GONE
        }

        when (vehicleReceived) {
            "Renegade" -> getBodyNotificationRenegade()
            "C5 Aircross" -> getBodyNotificationC5Aircross()
        }

        /*when (notifyReceived) {
            "IS_NOTIFICATION_NEW" -> bodyNotifications.visibility = View.VISIBLE
            "IS_NOT_NOTIFICATION_NEW" -> bodyNotificationsMaintenanceToBePlaned.visibility = View.VISIBLE
        }*/

        /*when (notifyReceived) {
            "IS_NOTIFICATION" -> bodyNotifications.visibility = View.VISIBLE
            "IS_NOT_NOTIFICATION" -> bodyNotificationsMaintenanceToBePlaned.visibility = View.VISIBLE
        }*/
        /*when (notifyReceived) {
            "IS_NOTIFICATION" -> bodyNotifications.visibility = View.VISIBLE
            "IS_NOT_NOTIFICATION" -> bodyNotificationsMaintenanceToBePlaned.visibility = View.VISIBLE
        }*/

        //Toast.makeText(activity, "!! --> " + argumentFromCoaching, Toast.LENGTH_SHORT).show()

        bodyNotifications.setOnClickListener {
            goToDetailsPage()
        }

        bodyNotificationsTile.setOnClickListener {
            goToDetailsPage()
        }
        /*bodyNotificationsMaintenanceToBePlanedTile.setOnClickListener {
            Toast.makeText(activity, "Critical", Toast.LENGTH_SHORT).show()
        }*/

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

    private fun getBodyNotificationRenegade() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getNotification()?.let { notificationNew ->

                            val notificationTitle = notificationNew.notifications?.get(1)?.notificationTitle
                            val notificationText = notificationNew.notifications?.get(1)?.notificationText

                            binding.idIncludeBodyNotifications.idTileNotificationToBePlanned.idNotificationTitle.text = notificationTitle
                            binding.idIncludeBodyNotifications.idTileNotificationToBePlanned.idNotificationText.text = notificationText


                        } ?: throw Exception("discovery null response")
                    }
                }
            } catch (e: Exception) {
                /*Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
                // Discovery error
                Log.e(ContentValues.TAG, "callDiscovery(): Error: $e")*/
            }
        }
    }

    private fun getBodyNotificationC5Aircross() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getNotificationC5()?.let { notificationNew ->

                            val notificationTitle = notificationNew.notifications?.get(1)?.notificationTitle
                            val notificationText = notificationNew.notifications?.get(1)?.notificationText

                            binding.idIncludeBodyNotifications.idTileNotificationToBePlanned.idNotificationTitle.text = notificationTitle
                            binding.idIncludeBodyNotifications.idTileNotificationToBePlanned.idNotificationText.text = notificationText


                        } ?: throw Exception("discovery null response")
                    }
                }
            } catch (e: Exception) {
                /*Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
                // Discovery error
                Log.e(ContentValues.TAG, "callDiscovery(): Error: $e")*/
            }
        }
    }
}