package com.stellantis.crf.pms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentVehicleInformationBinding

class VehicleInformationFragment : Fragment() {

    lateinit var binding: FragmentVehicleInformationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVehicleInformationBinding.inflate(layoutInflater)
        val view = binding.root

        binding.idIncludeToolbarVehicleInformation.textView.setOnClickListener {
            goToBack()
        }

        return view
    }

    private fun goToBack() {
        findNavController().navigate(R.id.action_vehicleInformationFragment_to_vehiclePageFragment2)
    }
}