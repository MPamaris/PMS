package com.stellantis.crf.pms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.databinding.FragmentHomeBinding
import com.stellantis.crf.pms.databinding.FragmentVehiclePageBinding

class VehiclePageFragment : Fragment() {

    lateinit var binding: FragmentVehiclePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVehiclePageBinding.inflate(layoutInflater)
        val view = binding.root

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
                    Log.i("NavBar","Error?")
                    false
                }
            }
        }

        return view
    }

    private fun goToHomePage() {
        findNavController().navigate(R.id.action_vehiclePageFragment_to_homeFragment)
    }
}