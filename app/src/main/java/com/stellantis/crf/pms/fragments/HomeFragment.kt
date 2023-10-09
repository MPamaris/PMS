package com.stellantis.crf.pms.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.PmsRepository
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentHomeBinding
import com.stellantis.crf.pms.model.NotificationInfo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

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
                    binding.idTvCheckVehicle.text = "Renegade"
                    getRenegadeInfo()

                }
                if (currentVehicle.contains("C5 Aircross")) {
                    binding.idTvCheckVehicle.text = "C5 Aircross"
                    getC5AirCrossInfo()
                }
            }

        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_dashboard -> {

                    true
                }

                R.id.page_navigation -> {

                    true
                }

                R.id.page_trips -> {

                    true
                }

                R.id.page_vehicle -> {
                    goToVehiclePage()
                    true
                }

                else -> {
                    Log.i("NavBar", "Error?")
                    false
                }
            }
        }

        binding.idIncludeTop.tileUserAndNotifications.constraintLayout.setOnClickListener {

            val isNotify = binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility

            if (isNotify == View.VISIBLE) {
                val isNotification = "IS_NOTIFICATION_NEW"
                val currentVehicle = binding.idTvCheckVehicle.text.toString()
                val status = NotificationInfo(isNotification, currentVehicle)

                val action = HomeFragmentDirections.actionHomeFragmentToNotificationsFragment(status)
                findNavController().navigate(action)
            }
            if (isNotify == View.INVISIBLE) {
                val isNotification = "IS_NOT_NOTIFICATION_NEW"
                val currentVehicle = binding.idTvCheckVehicle.text.toString()
                val status = NotificationInfo(isNotification, currentVehicle)

                val action = HomeFragmentDirections.actionHomeFragmentToNotificationsFragment(status)
                findNavController().navigate(action)
            }

        }

        binding.idIncludeTop.idTileChoiceVehicle.logo.setOnClickListener {
            toDetails()
        }

        binding.includeWarning.root.setOnClickListener {
            goToVehicleHealthPage()
        }

        binding.includeCardviewStatus.cardView.setOnClickListener {
            goToDetailsPage()
        }

        binding.includeCardviewFuel.root.setOnClickListener {
            goToUserFeedbackPage()
        }

        getComponents()
        getVehicleOwned()
        getVehicleInfoRenegade()
        getComponentsStatusC5Aircross()
        isNotification()
        isNotificationC5()
        getUser()
        setCurrentDateAndHour()

        return view
    }

    private fun getRenegadeInfo() {
        getVehicleInfo()
        getComponentsStatusRenegade()
        isNotification()
        binding.includeCardviewFuel.idProgressBarTileFuel.progress = 40

    }

    private fun getC5AirCrossInfo() {
        // MOCK type fuel
        getComponentsStatusC5Aircross()
        binding.includeCardviewFuel.textviewType.text = "Fuel"
        binding.includeCardviewFuel.textviewLevel.text = "70 %"
        binding.includeCardviewFuel.textviewAutonomy.text = "450 Km"
        binding.includeCardviewFuel.idProgressBarTileFuel.progress = 70
        isNotificationC5()

    }

    private fun getStatusC5AirCross() {


    }

    private fun getVehicleInfoRenegade() {
    }

    private fun goToNotificationPage() {
        findNavController().navigate(R.id.action_homeFragment_to_notificationsFragment)
    }

    private fun goToVehicleHealthPage() {
        findNavController().navigate(R.id.action_homeFragment_to_vehicleHealthPageFragment)
    }

    private fun toDetails() {
        findNavController().navigate(R.id.action_homeFragment_to_detailsPageFragment)
    }

    private fun goToVehiclePage() {
        findNavController().navigate(R.id.action_homeFragment_to_vehiclePageFragment2)
    }

    private fun goToDetailsPage() {
        findNavController().navigate(R.id.action_homeFragment_to_vehicleHealthPageFragment)
    }

    private fun goToUserFeedbackPage() {
        findNavController().navigate(R.id.action_homeFragment_to_userFeedbackFragment)
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

                            val criticalMaintenanceToBePlaned = notificationNew.notifications?.get(1)?.notificationType

                            /*when (notification) {
                                "0" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility =
                                    View.VISIBLE

                                "1" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility =
                                    View.INVISIBLE
                            }*/

                            if (notification.equals("0")) {
                                binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility = View.VISIBLE
                                //binding.includeWarning.root.visibility = View.VISIBLE
                                binding.includeWarning.idOutTextWarning.text = criticalMaintenanceToBePlaned
                                binding.includeWarning.iconMaintenance
                            } else {
                                binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility = View.INVISIBLE
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

    private fun isNotificationC5() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getNotificationC5()?.let { notificationNew ->

                            val notification =
                                notificationNew.notifications?.get(1)?.notificationSeverity

                            val criticalMaintenanceToBePlaned = notificationNew.notifications?.get(1)?.notificationType

                            /*when (notification) {
                                "0" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility =
                                    View.VISIBLE

                                "1" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility =
                                    View.INVISIBLE

                            }*/

                            if (notification.equals("0")) {
                                binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility = View.VISIBLE
                                //binding.includeWarning.root.visibility = View.VISIBLE
                                binding.includeWarning.idOutTextWarning.text = criticalMaintenanceToBePlaned
                                binding.includeWarning.iconMaintenance
                            } else {
                                binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility = View.INVISIBLE
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
    private fun getComponentsStatusRenegade() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getComponentsStatusRenegade()?.let { vehicleInfoNew ->

                            val healthOfComponent = vehicleInfoNew.components?.map { it.health }
                            val listOfComponent = vehicleInfoNew.components?.map { it.componentName }

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

                                binding.includeCardviewStatus.textviewStatus.text = "Check " + componentCritical
                                binding.includeCardviewStatus.idIconStatus.setImageResource(R.drawable.icon_alert)
                                binding.includeWarning.root.visibility = View.VISIBLE

                            }
                            else if (healthOfComponent.contains("2")) {
                                Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show()
                            }
                            else if (healthOfComponent.contains("3")) {
                                Toast.makeText(activity, "3", Toast.LENGTH_SHORT).show()
                            }
                            else
                                binding.includeCardviewStatus.textviewStatus.text = "All good"

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

    private fun getComponentsStatusC5Aircross() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getComponentsStatusC5Aircross()?.let { vehicleInfoNew ->

                            val healthOfComponent = vehicleInfoNew.components?.map { it.health }
                            val listOfComponent = vehicleInfoNew.components?.map { it.componentName }

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

                                binding.includeCardviewStatus.textviewStatus.text = "Check " + componentCritical
                                binding.includeCardviewStatus.idIconStatus.setImageResource(R.drawable.icon_alert)
                                binding.includeWarning.root.visibility = View.VISIBLE


                            }
                            else if (healthOfComponent.contains("2")) {
                                Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show()
                            }
                            else if (healthOfComponent.contains("3")) {
                                Toast.makeText(activity, "3", Toast.LENGTH_SHORT).show()
                            }
                            else
                                binding.includeCardviewStatus.textviewStatus.text = "All good"

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

                            binding.idIncludeCenter.tileWelcomeUser.textView3.text =
                                "LET'S GO\n" + name?.toUpperCase(
                                    Locale.ROOT
                                ) + "!"

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

                                val sharedPref: SharedPreferences = requireActivity().getSharedPreferences("FileName", MODE_PRIVATE)
                                val spinnerValue = sharedPref.getInt("spinnerChoice", -1)
                                if (spinnerValue != -1) binding.idIncludeTop.idTileChoiceVehicle.idChooseVehicleSpinner.setSelection(spinnerValue)
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
    private fun getVehicleInfo() {

        activity?.let { PmsRepository.initialize(it) }
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getVehicleInfo()?.let { vehicleInfoNew ->

                            val level = vehicleInfoNew.energies?.map { it.level }
                            val autonomy = vehicleInfoNew.energies?.map { it.autonomy }
                            val type = vehicleInfoNew.energies?.map { it.type }

                            binding.includeCardviewFuel.textviewType.text =
                                type.toString().replace("[", "").replace("]", "")
                                    .capitalize(Locale.ROOT)
                            binding.includeCardviewFuel.textviewLevel.text =
                                level.toString().replace("[", "").replace("]", "") + " %"
                            binding.includeCardviewFuel.textviewAutonomy.text =
                                autonomy.toString().replace("[", "").replace("]", "") + " Km"

                            binding.includeCardviewFuel.idProgressBarTileFuel.visibility = View.VISIBLE

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

    private fun setCurrentDateAndHour() {

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yy - HH:mm")
        val current = LocalDateTime.now().format(formatter)
        binding.idIncludeCenter.idTileDateAndHours.outTvDateAndHour.text = current

        binding.idIncludeCenter.idTileDateAndHours.buttonRefreshDateAndHour.setOnClickListener {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yy - HH:mm")
            val current = LocalDateTime.now().format(formatter)
            binding.idIncludeCenter.idTileDateAndHours.outTvDateAndHour.text = current
        }
    }

    private fun getComponents() {

    }
}