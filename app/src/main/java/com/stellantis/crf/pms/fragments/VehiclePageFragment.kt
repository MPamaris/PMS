package com.stellantis.crf.pms.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        binding.idIncludeTop.idTileChoiceVehicle.idChooseVehicleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val currentVehicle = binding.idIncludeTop.idTileChoiceVehicle.idChooseVehicleSpinner.selectedItem.toString()

                val Choice: Int = binding.idIncludeTop.idTileChoiceVehicle.idChooseVehicleSpinner.selectedItemPosition
                val sharedPrefS: SharedPreferences = activity!!.getSharedPreferences("FileName", 0)
                val prefEditor = sharedPrefS.edit()
                prefEditor.putInt("spinnerChoice", Choice)
                prefEditor.commit()
                //Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()

                if (currentVehicle.contains("Renegade")) {
                    getRenegadeInfo()
                    //binding.idTvCheckVehicle.text = "Renegade"
                    //Toast.makeText(activity, "" + currentVehicle, Toast.LENGTH_SHORT).show()

                }
                if (currentVehicle.contains("C5 Aircross")) {
                    //binding.idTvCheckVehicle.text = "C5 Aircross"
                    //Toast.makeText(activity, "" + currentVehicle, Toast.LENGTH_SHORT).show()
                    getC5AirCrossInfo()
                }
            }

        }

        /*isNotification()*/
        getUser()
        getVehicleOwned()

        val select_bottom_nav_default: View = binding.bottomNavigation.findViewById(com.stellantis.crf.pms.R.id.page_vehicle)
        select_bottom_nav_default.performClick()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.stellantis.crf.pms.R.id.page_dashboard -> {
                    goToHomePage()
                    true
                }

                com.stellantis.crf.pms.R.id.page_navigation -> {

                    true
                }

                com.stellantis.crf.pms.R.id.page_trips -> {

                    true
                }

                com.stellantis.crf.pms.R.id.page_vehicle -> {

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

                                val sharedPref: SharedPreferences =
                                    requireActivity().getSharedPreferences("FileName",
                                        Context.MODE_PRIVATE
                                    )
                                val spinnerValue = sharedPref.getInt("spinnerChoice", -1)
                                if (spinnerValue != -1) binding.idIncludeTop.idTileChoiceVehicle.idChooseVehicleSpinner.setSelection(
                                    spinnerValue
                                )
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

    private fun getRenegadeInfo() {
        getComponentsStatus()
        binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility = View.INVISIBLE

    }

    private fun getC5AirCrossInfo() {
        // MOCK type fuel
        isNotification()

    }

    private fun getComponentsStatus() {

        // TODO: TO BE COMPLETED

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getComponentsStatus()?.let { vehicleInfoNew ->

                            val healthOfComponent = vehicleInfoNew.components?.map { it.health }
                            /*binding.includeCardviewStatus.idTextViewStatus.text =
                                healthOfComponent?.get(1)*/

                            if (healthOfComponent!!.contains("1")) {
                                Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show()
                            }
                            else if (healthOfComponent.contains("2")) {
                                Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show()
                            }
                            else if (healthOfComponent.contains("3")) {
                                Toast.makeText(activity, "3", Toast.LENGTH_SHORT).show()
                            }
                            //Toast.makeText(activity, "!! " + healthOfComponent, Toast.LENGTH_SHORT).show()

                            //Toast.makeText(activity, "!! --> " + healthOfComponent?.get(0), Toast.LENGTH_SHORT).show()

                            /*for (i in healthOfComponent!!.indices) {

                                Toast.makeText(activity, "FOR --> " + healthOfComponent[i], Toast.LENGTH_SHORT).show()

                            }*/


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
        findNavController().navigate(com.stellantis.crf.pms.R.id.action_vehiclePageFragment_to_homeFragment)
    }
}