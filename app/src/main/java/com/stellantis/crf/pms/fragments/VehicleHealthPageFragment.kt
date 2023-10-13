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
import com.stellantis.crf.pms.model.ArgumentsToDetailsPageInfo
import com.stellantis.crf.pms.model.NotificationInfo

class VehicleHealthPageFragment : Fragment() {

    lateinit var binding: FragmentVehicleHealthPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVehicleHealthPageBinding.inflate(layoutInflater)
        val view = binding.root

        binding.idTileVehicleHealthComponentExpandable.idLayoutBattery.setOnClickListener {
            clickOnBatteryLayout()
        }
        binding.idTileVehicleHealthComponentExpandable.idLayoutBrakePads.setOnClickListener {
            clickOnBrakePadsLayout()
        }
        binding.idTileVehicleHealthComponentExpandable.idLayoutBrakeDisc.setOnClickListener {
            clickOnBrakeDisksLayout()
        }
        binding.idTileVehicleHealthComponentExpandable.idLayoutDiesel.setOnClickListener {
            clickOnDieselLayout()
        }
        binding.idTileVehicleHealthComponentExpandable.idLayoutRearTires.setOnClickListener {
            clickOnTiresLayout()
        }
        binding.idTileVehicleHealthComponentExpandable.idLayoutOilFilter.setOnClickListener {
            clickOnAirFilterLayout()
        }
        binding.idTileVehicleHealthComponentExpandable.idLayoutHeadlightBulbs.setOnClickListener {
            clickOnBulbsLayout()
        }
        binding.idTileVehicleHealthComponentExpandable.idLayoutShockAbsorber.setOnClickListener {
            clickOnEngineLayout()
        }


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

    private fun clickOnBatteryLayout() {
        val isBatteryPassed = "IS_BATTERY"
        val arg_passed = ArgumentsToDetailsPageInfo(isBatteryPassed)

        val action = VehicleHealthPageFragmentDirections.actionVehicleHealthPageFragmentToDetailsPageFragment(arg_passed)
        findNavController().navigate(action)
    }
    private fun clickOnBrakePadsLayout() {
        val isBrakePadsPassed = "IS_BRAKE_PADS"
        val arg_passed = ArgumentsToDetailsPageInfo(isBrakePadsPassed)

        val action = VehicleHealthPageFragmentDirections.actionVehicleHealthPageFragmentToDetailsPageFragment(arg_passed)
        findNavController().navigate(action)
    }
    private fun clickOnBrakeDisksLayout() {
        val isBrakeDisksPassed = "IS_BRAKE_DISKS"
        val arg_passed = ArgumentsToDetailsPageInfo(isBrakeDisksPassed)

        val action = VehicleHealthPageFragmentDirections.actionVehicleHealthPageFragmentToDetailsPageFragment(arg_passed)
        findNavController().navigate(action)
    }
    private fun clickOnDieselLayout() {
        val isDieselPassed = "IS_DIESEL"
        val arg_passed = ArgumentsToDetailsPageInfo(isDieselPassed)

        val action = VehicleHealthPageFragmentDirections.actionVehicleHealthPageFragmentToDetailsPageFragment(arg_passed)
        findNavController().navigate(action)
    }
    private fun clickOnTiresLayout() {
        val isBrakeTiresPassed = "IS_TIRES"
        val arg_passed = ArgumentsToDetailsPageInfo(isBrakeTiresPassed)

        val action = VehicleHealthPageFragmentDirections.actionVehicleHealthPageFragmentToDetailsPageFragment(arg_passed)
        findNavController().navigate(action)
    }
    private fun clickOnAirFilterLayout() {
        val isAirFilterPassed = "IS_AIR_FILTER"
        val arg_passed = ArgumentsToDetailsPageInfo(isAirFilterPassed)

        val action = VehicleHealthPageFragmentDirections.actionVehicleHealthPageFragmentToDetailsPageFragment(arg_passed)
        findNavController().navigate(action)
    }
    private fun clickOnBulbsLayout() {
        val isBulbsPassed = "IS_BULBS"
        val arg_passed = ArgumentsToDetailsPageInfo(isBulbsPassed)

        val action = VehicleHealthPageFragmentDirections.actionVehicleHealthPageFragmentToDetailsPageFragment(arg_passed)
        findNavController().navigate(action)
    }
    private fun clickOnEngineLayout() {
        val isEnginePassed = "IS_ENGINE"
        val arg_passed = ArgumentsToDetailsPageInfo(isEnginePassed)

        val action = VehicleHealthPageFragmentDirections.actionVehicleHealthPageFragmentToDetailsPageFragment(arg_passed)
        findNavController().navigate(action)
    }
}