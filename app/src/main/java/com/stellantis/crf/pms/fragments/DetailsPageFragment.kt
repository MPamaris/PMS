package com.stellantis.crf.pms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentDetailsPageBinding

class DetailsPageFragment : Fragment() {

    lateinit var binding: FragmentDetailsPageBinding
    private val args: DetailsPageFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsPageBinding.inflate(layoutInflater)
        val view = binding.root

        val componentReceived = args.argumentsToDetailsPage.argumentsPassed
        val componentInCriticalReceived = args.argumentsToDetailsPage.inCriticalFromHome

        if (componentInCriticalReceived.isNotEmpty()) {
            setInCaseOfCritical()
        } else {
            when (componentReceived) {
                "IS_BATTERY" -> setCaseBatteryAllGood()
                "IS_BRAKE_PADS" -> setCaseBrakePadsAllGood()
                "IS_BRAKE_DISKS" -> setCaseBrakeDisksAllGood()
                "IS_DIESEL" -> setCaseDieselAllGood()
                "IS_TIRES" -> setCaseDieselAllGood()
                "IS_AIR_FILTER" -> setCaseAirFilterAllGood()
                "IS_BULBS" -> setCaseBulbsAllGood()
                "IS_ENGINE" -> setCaseEngineAllGood()
            }
        }


        binding.idIncludeToolbarDetailsPage.idButtonBackToolbarNotifications.setOnClickListener {
            goToHomePage()
        }

        binding.idTileDetailsPageInclude.idArrowExpandDescription.setOnClickListener {
            binding.idTileDetailsPageInclude.idDescriptionToExpand.visibility = View.VISIBLE
        }

        binding.idTileDetailsPageInclude.idArrowCloseDescription.setOnClickListener {
            binding.idTileDetailsPageInclude.idDescriptionToExpand.visibility = View.GONE
        }

        /*binding.includeCoachingAdviseLongVehicleStorage.idViewMoreCoachAdvise.setOnClickListener {
            goToMoreCoachAdvise()
        }

        binding.includeBodyDetailsPageNew.idButtonShowMoreStateOfHealth.setOnClickListener {
            expandStateOfHealth()
        }

        binding.includeCoachingAdviseLongVehicleStorage.idButtonExpandPossibleRisk.setOnClickListener {
            expandCoachingAdvise()
        }*/

        return view
    }

    private fun goToHomePage() {
        findNavController().navigate(R.id.action_detailsPageFragment_to_vehicleHealthPageFragment)
    }

    private fun goToMoreCoachAdvise() {
        findNavController().navigate(R.id.action_detailsPageFragment_to_coachingAdviseFragment)
    }

    /*private fun expandStateOfHealth() {

        val arrow =
            binding.includeBodyDetailsPageNew.idButtonShowMoreStateOfHealth

        if ("down" == arrow.tag) {
            binding.includeBodyDetailsPageNew.idButtonShowMoreStateOfHealth.setImageResource(
                R.drawable.arrow_up
            )
            binding.includeBodyDetailsPageNew.idStateOfHealthExpandable.visibility =
                View.VISIBLE
            binding.includeBodyDetailsPageNew.idCardviewTextDesc.visibility =
                View.VISIBLE
            binding.includeBodyDetailsPage.root.visibility = View.GONE
            arrow.tag = "up"
            binding.includeBodyDetailsPageNew.idTvShowMore.text = "Show less"
        } else {
            binding.includeBodyDetailsPageNew.idButtonShowMoreStateOfHealth.setImageResource(
                R.drawable.baseline_keyboard_arrow_down_24
            )
            binding.includeBodyDetailsPageNew.idStateOfHealthExpandable.visibility =
                View.GONE
            binding.includeBodyDetailsPageNew.idCardviewTextDesc.visibility =
                View.GONE
            arrow.tag = "down"
            binding.includeBodyDetailsPage.root.visibility = View.VISIBLE
            binding.includeBodyDetailsPageNew.idTvShowMore.text = "Show more"
        }

    }

    private fun expandCoachingAdvise() {

        val arrow =
            binding.includeCoachingAdviseLongVehicleStorage.idButtonExpandPossibleRisk

        if ("down" == arrow.tag) {
            binding.includeCoachingAdviseLongVehicleStorage.idButtonExpandPossibleRisk.setImageResource(
                R.drawable.arrow_up
            )
            binding.includeCoachingAdviseLongVehicleStorage.idCoachingAdviseExpandable.visibility =
                View.VISIBLE
            binding.includeCoachingAdviseLongVehicleStorage.thisLayout.visibility =
                View.VISIBLE
            binding.idCardviewTextDesc.visibility = View.GONE
            arrow.tag = "up"
            binding.includeBodyDetailsPageNew.root.visibility = View.GONE
            binding.includeBodyDetailsPage.root.visibility = View.GONE
        } else {
            binding.includeCoachingAdviseLongVehicleStorage.idButtonExpandPossibleRisk.setImageResource(
                R.drawable.baseline_keyboard_arrow_down_24
            )
            binding.includeCoachingAdviseLongVehicleStorage.idCoachingAdviseExpandable.visibility =
                View.GONE
            binding.includeCoachingAdviseLongVehicleStorage.thisLayout.visibility =
                View.GONE
            arrow.tag = "down"
            binding.includeBodyDetailsPageNew.root.visibility = View.VISIBLE
            binding.includeBodyDetailsPage.root.visibility = View.VISIBLE
        }

    }*/

    private fun setCaseBatteryAllGood() {
        val component = "Battery"
        binding.idIncludeToolbarDetailsPage.idToolbarDetails.text = component + " details"
        binding.idTileComponentStateOfHealth.idOutStateOfHealth.text = component.toUpperCase() + " STATE OF HEALTH"
        binding.idTileComponentStateOfHealth.idIconStateGood.setImageResource(R.drawable.state_good_health)
    }
    private fun setCaseBrakePadsAllGood() {
        val component = "Brake Pads"
        binding.idIncludeToolbarDetailsPage.idToolbarDetails.text = component + " details"
        binding.idTileComponentStateOfHealth.idOutStateOfHealth.text = component.toUpperCase() + " STATE OF HEALTH"
        binding.idTileComponentStateOfHealth.idIconStateGood.setImageResource(R.drawable.state_good_health)
    }
    private fun setCaseBrakeDisksAllGood() {
        val component = "Brake Disc"
        binding.idIncludeToolbarDetailsPage.idToolbarDetails.text = component + " details"
        binding.idTileComponentStateOfHealth.idOutStateOfHealth.text = component.toUpperCase() + " STATE OF HEALTH"
        binding.idTileComponentStateOfHealth.idIconStateGood.setImageResource(R.drawable.state_good_health)
    }
    private fun setCaseDieselAllGood() {
        val component = "Diesel particles filter"
        binding.idIncludeToolbarDetailsPage.idToolbarDetails.text = component + " details"
        binding.idTileComponentStateOfHealth.idOutStateOfHealth.text = component.toUpperCase() + " STATE OF HEALTH"
        binding.idTileComponentStateOfHealth.idIconStateGood.setImageResource(R.drawable.state_good_health)
    }
    private fun setCaseTiresAllGood() {
        val component = "Tires"
        binding.idIncludeToolbarDetailsPage.idToolbarDetails.text = component + " details"
        binding.idTileComponentStateOfHealth.idOutStateOfHealth.text = component.toUpperCase() + " STATE OF HEALTH"
        binding.idTileComponentStateOfHealth.idIconStateGood.setImageResource(R.drawable.state_good_health)
    }
    private fun setCaseAirFilterAllGood() {
        val component = "Air Filter"
        binding.idIncludeToolbarDetailsPage.idToolbarDetails.text = component + " details"
        binding.idTileComponentStateOfHealth.idOutStateOfHealth.text = component.toUpperCase() + " STATE OF HEALTH"
        binding.idTileComponentStateOfHealth.idIconStateGood.setImageResource(R.drawable.state_good_health)
    }
    private fun setCaseBulbsAllGood() {
        val component = "Headlight bulbs"
        binding.idIncludeToolbarDetailsPage.idToolbarDetails.text = component + " details"
        binding.idTileComponentStateOfHealth.idOutStateOfHealth.text = component.toUpperCase() + " STATE OF HEALTH"
        binding.idTileComponentStateOfHealth.idIconStateGood.setImageResource(R.drawable.state_good_health)
    }
    private fun setCaseEngineAllGood() {
        val component = "Engine"
        binding.idIncludeToolbarDetailsPage.idToolbarDetails.text = component + " details"
        binding.idTileComponentStateOfHealth.idOutStateOfHealth.text = component.toUpperCase() + " STATE OF HEALTH"
        binding.idTileComponentStateOfHealth.idIconStateGood.setImageResource(R.drawable.state_good_health)
    }

    private fun setInCaseOfCritical() {
        val componentInCriticalReceived = args.argumentsToDetailsPage.inCriticalFromHome
        binding.idIncludeToolbarDetailsPage.idToolbarDetails.text = componentInCriticalReceived + " details"
        binding.idTileComponentStateOfHealth.idOutStateOfHealth.text = componentInCriticalReceived.toUpperCase() + " STATE OF HEALTH"
        binding.idTileComponentStateOfHealth.idIconStateToBePlanned.setImageResource(R.drawable.state_critical_maintenance_to_be_planed)
    }
}