package com.stellantis.crf.pms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentVehicleHealthPageBinding

class VehicleHealthPageFragment : Fragment() {

    lateinit var binding: FragmentVehicleHealthPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVehicleHealthPageBinding.inflate(layoutInflater)
        val view = binding.root

        binding.idIncludeToolbarNotifications.textView.setOnClickListener {
            goToHomePage()
        }

        binding.idTileVehicleHealthComponentExpandable.idArrowExpandableComponents.setOnClickListener {
            binding.idTileVehicleHealthComponentExpandable.idLayoutFather.visibility = View.GONE
            binding.idTileVehicleHealthComponentExpandable.idLayoutChild.visibility = View.VISIBLE
        }

        binding.idTileVehicleHealthComponentExpandable.idArrowChild.setOnClickListener {
            binding.idTileVehicleHealthComponentExpandable.idLayoutChild.visibility = View.GONE
            binding.idTileVehicleHealthComponentExpandable.idLayoutFather.visibility = View.VISIBLE
        }
        return view
    }

    private fun goToHomePage() {
        findNavController().navigate(R.id.action_vehicleHealthPageFragment_to_vehiclePageFragment)
    }

    private fun goToComponentsDetailsPage() {
        findNavController().navigate(R.id.action_vehicleHealthPageFragment_to_detailsPageFragment)
    }

    private fun goToCoachingAdvisePage() {
        findNavController().navigate(R.id.action_vehicleHealthPageFragment_to_coachingAdviseFragment)
    }
}