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

            /*val isNotifyOn = "OFF"
            val noti = NotificationInfo(isNotify)

            val action = HomeFragmentDirections.actionHomeFragmentToNotificationsFragment(noti)
            findNavController().navigate(action)*/

            val isNotify = binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification
            if (isNotify.visibility == View.VISIBLE) {
                val isNotifyOn = "IS_NOTIFICATION"
                val selectedVehicle = binding.idTvCheckVehicle.text.toString()
                val noti = NotificationInfo(isNotifyOn, selectedVehicle)

                val action = HomeFragmentDirections.actionHomeFragmentToNotificationsFragment(noti)
                findNavController().navigate(action)
                //goToNotificationPage()
                //Toast.makeText(activity, "VISIBLE", Toast.LENGTH_SHORT).show()
            }
            if (isNotify.visibility != View.VISIBLE) {
                val isNotifyOn = "IS_NOT_NOTIFICATION"
                val selectedVehicle = binding.idTvCheckVehicle.text.toString()
                val noti = NotificationInfo(isNotifyOn, selectedVehicle)
                // MEMORIZZARE VEICOLO SCELTO

                val action = HomeFragmentDirections.actionHomeFragmentToNotificationsFragment(noti)
                findNavController().navigate(action)
                //goToNotificationPage()
                //Toast.makeText(activity, "NOT VISIBLE", Toast.LENGTH_SHORT).show()
            }
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

        /*binding.idIncludeTop.idTileChoiceVehicle.idLogo.setOnClickListener {
            Toast.makeText(activity, "!!", Toast.LENGTH_SHORT).show()
            binding.idTvCheckVehicle.text = "!!"
        }*/

        /*Timer().schedule(object : TimerTask() {
            override fun run() {
                getVehicleOwned()
            }
        }, 1000)*/
        getVehicleOwned()
        getVehicleInfoRenegade()

        //isNotification()
        //getComponentsStatus()
        getUser()
        setCurrentDateAndHour()

        // TODO: change status on tiles based on choose vehicle

        return view
    }

    private fun getRenegadeInfo() {
        binding.includeWarning.cardView.visibility = View.GONE
        getVehicleInfo()
        getComponentsStatus()
        // enable/disable notifications
        isNotification()
        binding.includeCardviewFuel.idProgressBarTileFuel.progress = 40
        binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility = View.INVISIBLE

    }

    private fun getC5AirCrossInfo() {
        binding.includeWarning.cardView.visibility = View.VISIBLE
        // MOCK type fuel
        binding.includeCardviewFuel.textviewType.text = "Fuel"
        binding.includeCardviewFuel.textviewLevel.text = "70 %"
        binding.includeCardviewFuel.textviewAutonomy.text = "450 Km"
        binding.includeCardviewFuel.idProgressBarTileFuel.progress = 70
        getStatusC5AirCross()

    }

    private fun getStatusC5AirCross() {
        isNotification()
        binding.includeCardviewStatus.textviewStatus.text = "Check battery"
        binding.includeCardviewStatus.idIconStatus.setImageResource(R.drawable.icon_alert)

    }

    private fun getVehicleInfoRenegade() {
        /*getComponentsStatus()
        binding.includeWarning.root.visibility = View.GONE
        getVehicleInfo()*/
    }

    private fun goToNotificationPage() {
        findNavController().navigate(R.id.action_homeFragment_to_notificationsFragment)
    }

    private fun goToVehicleHealthPage() {
        findNavController().navigate(R.id.action_homeFragment_to_vehicleHealthPageFragment)
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
                                notificationNew.notifications?.get(0)?.notificationSeverity

                            when (notification) {
                                "0" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility =
                                    View.VISIBLE

                                "1" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility =
                                    View.INVISIBLE

                            }

                            binding.includeWarning.idOutTextWarning.text =
                                notificationNew.notifications?.map { it.notificationType.toString() }
                                    .toString().replace("[", "").replace("]", "")


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
                            else
                                binding.includeCardviewStatus.textviewStatus.text = "All good"
                                binding.includeCardviewStatus.idIconStatus.setImageResource(R.drawable.baseline_done_24)



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
}