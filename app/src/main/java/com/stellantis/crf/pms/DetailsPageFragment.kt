package com.stellantis.crf.pms

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.databinding.FragmentDetailsPageBinding
import com.stellantis.crf.pms.databinding.FragmentVehicleHealthPageBinding

class DetailsPageFragment : Fragment() {

    lateinit var binding: FragmentDetailsPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsPageBinding.inflate(layoutInflater)
        val view = binding.root


        binding.idIncludeToolbarDetailsPage.idButtonBackToolbarNotifications.setOnClickListener {
            goToHomePage()
        }

        return view
    }

    private fun goToHomePage() {
        findNavController().navigate(R.id.action_detailsPageFragment_to_vehicleHealthPageFragment)
    }
}