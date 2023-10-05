package com.stellantis.crf.pms

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.stellantis.crf.pms.databinding.ActivityMainBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Locale


class PmsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getVehicleInfo()
        getVehicleOwned()
        getUser()
        getComponentsStatus()
        isNotification()

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

    private fun getVehicleOwned() {

        PmsRepository.initialize(applicationContext)
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getVehicleOwned()?.let { vehicleInfoNew ->

                            val model = vehicleInfoNew.vehicles?.map { it.model }

                            val str = model.toString().replace("[", " ").replace("]", "")
                            val delimiter = ","
                            val partsOfListOfVehicles = str.split(delimiter)

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

    private fun getComponentsStatus() {

        PmsRepository.initialize(applicationContext)
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        PmsRepository.getComponentsStatusRenegade()?.let { vehicleInfoNew ->


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
    override fun onBackPressed() {

    }
}