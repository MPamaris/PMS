package com.stellantis.crf.pms.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.PmsRepository
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentVehiclePageBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

class VehiclePageFragment : Fragment() {

    lateinit var binding: FragmentVehiclePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVehiclePageBinding.inflate(layoutInflater)
        val view = binding.root

        isNotification()
        getUser()
        getVehicleOwned()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_dashboard -> {
                    goToHomePage()
                    true
                }

                R.id.page_navigation -> {
                    Toast.makeText(activity, "page_navigation", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.page_trips -> {
                    Toast.makeText(activity, "page_trips", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.page_vehicle -> {
                    Toast.makeText(activity, "page_vehicle", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    Log.i("NavBar", "Error?")
                    false
                }
            }
        }

        return view
    }

    private fun isNotification() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getNotification()?.let { notificationNew ->

                            val notification =
                                notificationNew.notifications?.get(0)?.notificationSeverity

                            when (notification) {
                                "0" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility =
                                    View.VISIBLE

                                "1" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility =
                                    View.INVISIBLE

                            }

                        } ?: throw Exception("discovery null response")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
                // Discovery error
                Log.e(ContentValues.TAG, "callDiscovery(): Error: $e")
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getUser() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getUserInfo()?.let { userInfoNew ->
                            val name = userInfoNew.name
                            val surname = userInfoNew.surname
                            val initialName =
                                userInfoNew.name?.get(0).toString().toUpperCase(Locale.ROOT)
                            val initialSurname =
                                userInfoNew.surname?.get(0).toString().toUpperCase(Locale.ROOT)

                            binding.idIncludeTop.tileUserAndNotifications.idTexvViewOutInitials.text =
                                initialName + initialSurname

                        } ?: throw Exception("discovery null response")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
                // Discovery error
                Log.e(ContentValues.TAG, "callDiscovery(): Error: $e")
            }
        }
    }
    private fun getVehicleOwned() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getVehicleOwned()?.let { vehicleInfoNew ->

                            val model = vehicleInfoNew.vehicles?.map { it.model }

                            val str = model.toString().replace("[", " ").replace("]", "")
                            val delimiter = ","
                            val partsOfListOfVehicles = str.split(delimiter)

                            for (i in partsOfListOfVehicles.indices) {
                                val spinner =
                                    binding.idIncludeTop.idTileChoiceVehicle.idChooseVehicleSpinner
                                val dataAdapter: ArrayAdapter<String>? = activity?.let {
                                    ArrayAdapter<String>(
                                        it,
                                        android.R.layout.simple_spinner_item, partsOfListOfVehicles
                                    )
                                }
                                spinner.adapter = dataAdapter
                            }

                        } ?: throw Exception("discovery null response")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
                // Discovery error
                Log.e(ContentValues.TAG, "callDiscovery(): Error: $e")
            }
        }
    }

    private fun goToHomePage() {
        findNavController().navigate(R.id.action_vehiclePageFragment_to_homeFragment)
    }
}