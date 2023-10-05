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
import com.stellantis.crf.pms.model.NotificationInfo
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

                if (currentVehicle.contains("Renegade")) {
                    getRenegadeInfo()
                    binding.idTvCheckVehicle.text = "Renegade"

                }
                if (currentVehicle.contains("C5 Aircross")) {
                    binding.idTvCheckVehicle.text = "C5 Aircross"
                    getC5AirCrossInfo()
                }
            }

        }

        binding.idIncludeTop.tileUserAndNotifications.constraintLayout.setOnClickListener {

            val isNotify = binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility

            if (isNotify == View.VISIBLE) {
                val isNotification = "IS_NOTIFICATION_NEW"
                val currentVehicle = binding.idTvCheckVehicle.text.toString()
                val status = NotificationInfo(isNotification, currentVehicle)

                val action = VehiclePageFragmentDirections.actionVehiclePageFragmentToNotificationsFragment(status)
                findNavController().navigate(action)
            }
            if (isNotify == View.INVISIBLE) {
                val isNotification = "IS_NOT_NOTIFICATION_NEW"
                val currentVehicle = binding.idTvCheckVehicle.text.toString()
                val status = NotificationInfo(isNotification, currentVehicle)

                val action = VehiclePageFragmentDirections.actionVehiclePageFragmentToNotificationsFragment(status)
                findNavController().navigate(action)
            }

        }
        binding.idTileVehiclePageInformation.idVehicleInformation.setOnClickListener {
            goToVehicleInfoPage()
        }

        binding.idTileVehiclePageHealth.idVehicleHealth.setOnClickListener {
            goToVehicleHealthPage()
        }

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
                                notificationNew.notifications?.get(1)?.notificationSeverity

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
                /*Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
                // Discovery error
                Log.e(ContentValues.TAG, "callDiscovery(): Error: $e")*/
                binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility =
                    View.INVISIBLE
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
        isNotification()
        isGetVinRenegade()
        binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility = View.INVISIBLE

    }

    private fun getC5AirCrossInfo() {
        // MOCK type fuel
        isNotification()
        isGetVinC5Aircross()
        getComponentsStatus()

    }

    @SuppressLint("ResourceAsColor")
    private fun getComponentsStatus() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getComponentsStatusRenegade()?.let { vehicleInfoNew ->

                            val healthOfComponent = vehicleInfoNew.components?.map { it.health }

                            if (healthOfComponent!!.contains("1")) {
                                data class Component(val name: String, val status: Int)

                                val battery = healthOfComponent[0]!!.toInt()
                                val brakePads = healthOfComponent[1]!!.toInt()
                                val brakeDiscs = healthOfComponent[2]!!.toInt()
                                val dieselParticlesFilter = healthOfComponent[3]!!.toInt()
                                val tire = healthOfComponent[4]!!.toInt()
                                val airFilter = healthOfComponent[5]!!.toInt()
                                val headlightBulbs = healthOfComponent[6]!!.toInt()
                                val engine = healthOfComponent[7]!!.toInt()

                                val singleComponent = listOf(
                                    Component("Battery", battery),
                                    Component("Brake Pads", brakePads),
                                    Component("Brake Discs", brakeDiscs),
                                    Component("Diesel Particles Filter", dieselParticlesFilter),
                                    Component("Tire", tire),
                                    Component("Air Filter", airFilter),
                                    Component("Head Light Bulbs", headlightBulbs),
                                    Component("Engine", engine),
                                )

                                val componentCritical = singleComponent.filter { it.status == 1}.toString().replace("[","")
                                    .replace("Component","")
                                    .replace("name","")
                                    .replace("=","")
                                    .replace(",","")
                                    .replace("status","")
                                    .replace("(","")
                                    .replace(")","")
                                    .replace("1","")
                                    .replace("]","")

                                binding.idTileVehiclePageHealth.idTileVehicleMaintenance.visibility = View.VISIBLE
                                binding.idTileVehiclePageHealth.idTileVehicleHealth.visibility = View.GONE
                            }
                            else if (healthOfComponent.contains("2")) {
                                Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show()
                            }
                            else if (healthOfComponent.contains("3")) {
                                Toast.makeText(activity, "3", Toast.LENGTH_SHORT).show()
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

    private fun isGetVinRenegade() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getVinRenegade()?.let { notificationNew ->

                            val vin = notificationNew.notifications?.get(0)?.vin
                            binding.idTileVehiclePageInformation.idVehicleVinNumber.text = vin


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
    private fun isGetVinC5Aircross() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getVinC5Aircross()?.let { notificationNew ->

                            val vin = notificationNew.notifications?.get(1)?.vin
                            binding.idTileVehiclePageInformation.idVehicleVinNumber.text = vin



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

    private fun goToVehicleInfoPage() {
        findNavController().navigate(R.id.action_vehiclePageFragment_to_vehicleInformationFragment)
    }

    private fun goToVehicleHealthPage() {
        findNavController().navigate(R.id.action_vehiclePageFragment_to_vehicleHealthPageFragment)
    }
}