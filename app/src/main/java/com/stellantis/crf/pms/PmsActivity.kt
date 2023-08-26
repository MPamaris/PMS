package com.stellantis.crf.pms

import android.R
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.stellantis.crf.pms.databinding.ActivityMainBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class PmsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idIncludeTop.tileUserAndNotifications.constraintLayout.setOnClickListener { goToNotificationPage() }

        //hideNavAndStatusBar()
        //callDiscovery()
        getVehicleInfo()
        getUser()
        setCurrentDateAndHour()

        isNotification()

        // TODO: commit ok, try push 

    }

    private fun goToNotificationPage() {
        val intent = Intent(this, NotificationsActivity::class.java)
        startActivity(intent)
        // TODO: replace wit navigation
    }


    @SuppressLint("SetTextI18n")
    private fun isNotification() {

        PmsRepository.initialize(applicationContext)
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

                            binding.includeWarning.idOutTextWarning.text = notificationNew.notifications?.map { it.notificationType.toString() }
                                .toString().replace("[","").replace("]","")


                        } ?: throw Exception("discovery null response")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@PmsActivity, "Network Error", Toast.LENGTH_LONG).show()
                // Discovery error
                Log.e(ContentValues.TAG, "callDiscovery(): Error: $e")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getUser() {

        PmsRepository.initialize(applicationContext)
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getUserInfo()?.let { userInfoNew ->
                            //val user = userInfoNew.user?.get(0)
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
                            //Toast.makeText(applicationContext, "--> " + initialName+initialSurname, Toast.LENGTH_SHORT).show()
                            //Toast.makeText(applicationContext, "name--> " + name+"\nsurame --> " + surname , Toast.LENGTH_SHORT).show()
                            // retrieve Ada[1] {Notification false}
                            // retrieve Marc[14] {Notification true}
                            /*val user = userInfoNew.items?.get(14)
                            binding.idIncludeCenter.tileWelcomeUser.textView3.text = "LET'S GO\n" + user?.name.toString().toUpperCase()+"!"
                            val isNotification = user?.status.toString()
                            when (isNotification) {
                                "0" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility = View.INVISIBLE
                                "1" -> binding.idIncludeTop.tileUserAndNotifications.idBadgeNotification.visibility = View.VISIBLE
                            }*/


                            //Toast.makeText(applicationContext, "--> " + user, Toast.LENGTH_SHORT).show()


                        } ?: throw Exception("discovery null response")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@PmsActivity, "Network Error", Toast.LENGTH_LONG).show()
                // Discovery error
                Log.e(ContentValues.TAG, "callDiscovery(): Error: $e")
            }
        }
    }

    private fun getVehicleInfo() {

        PmsRepository.initialize(applicationContext)
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getVehicleInfo()?.let { vehicleInfoNew ->

                            val level = vehicleInfoNew.energies?.map { it.level }
                            val autonomy = vehicleInfoNew.energies?.map { it.autonomy }
                            val type = vehicleInfoNew.energies?.map { it.type }

                            binding.includeCardviewFuel.textviewType.text = type.toString().replace("[","").replace("]","").capitalize(Locale.ROOT)
                            binding.includeCardviewFuel.textviewLevel.text = level.toString().replace("[","").replace("]","")
                            binding.includeCardviewFuel.textviewAutonomy.text = autonomy.toString().replace("[","").replace("]","")+" Km"

                        } ?: throw Exception("discovery null response")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@PmsActivity, "Network Error", Toast.LENGTH_LONG).show()
                // Discovery error
                Log.e(ContentValues.TAG, "callDiscovery(): Error: $e")
            }
        }
    }

    private fun callDiscovery() {

        PmsRepository.initialize(applicationContext)
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getVehicleInfoOld()?.let { vehicleInfoNew ->
                            val listOfVehicles = vehicleInfoNew.items?.map {
                                it.autonomy
                            }?.toSet()
                            val str = listOfVehicles.toString().replace("[", " ").replace("]", "")
                            val delimiter = ","
                            val partsOfListOfVehicles = str.split(delimiter)

                            for (i in partsOfListOfVehicles.indices) {
                                val spinner =
                                    binding.idIncludeTop.idTileChoiceVehicle.idChooseVehicleSpinner
                                val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                    applicationContext,
                                    R.layout.simple_spinner_item, partsOfListOfVehicles
                                )
                                spinner.adapter = dataAdapter
                            }
                        } ?: throw Exception("discovery null response")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@PmsActivity, "Network Error", Toast.LENGTH_LONG).show()
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
    /*private fun hideNavAndStatusBar() {
            window.decorView.apply {
                //hide both the navigation bar and the status bar.
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
            }
        }*/

    // TODO: here
}