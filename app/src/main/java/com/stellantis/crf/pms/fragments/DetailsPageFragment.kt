package com.stellantis.crf.pms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentDetailsPageBinding

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

        binding.includeCoachingAdviseLongVehicleStorage.idViewMoreCoachAdvise.setOnClickListener {
            goToMoreCoachAdvise()
        }

        binding.includeBodyDetailsPageNew.idButtonShowMoreStateOfHealth.setOnClickListener {
            expandStateOfHealth()
        }

        binding.includeCoachingAdviseLongVehicleStorage.idButtonExpandPossibleRisk.setOnClickListener {
            expandCoachingAdvise()
        }

        return view
    }

    private fun goToHomePage() {
        findNavController().navigate(R.id.action_detailsPageFragment_to_vehicleHealthPageFragment)
    }

    private fun goToMoreCoachAdvise() {
        findNavController().navigate(R.id.action_detailsPageFragment_to_coachingAdviseFragment)
    }

    private fun expandStateOfHealth() {

        val arrow =
            binding.includeBodyDetailsPageNew.idButtonShowMoreStateOfHealth

        if ("down" == arrow.tag) {
            binding.includeBodyDetailsPageNew.idButtonShowMoreStateOfHealth.setImageResource(
                R.drawable.baseline_keyboard_arrow_up_24
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
                R.drawable.baseline_keyboard_arrow_up_24
            )
            binding.includeCoachingAdviseLongVehicleStorage.idCoachingAdviseExpandable.visibility =
                View.VISIBLE
            arrow.tag = "up"
            binding.includeBodyDetailsPageNew.root.visibility = View.GONE
            binding.includeBodyDetailsPage.root.visibility = View.GONE
        } else {
            binding.includeCoachingAdviseLongVehicleStorage.idButtonExpandPossibleRisk.setImageResource(
                R.drawable.baseline_keyboard_arrow_down_24
            )
            binding.includeCoachingAdviseLongVehicleStorage.idCoachingAdviseExpandable.visibility =
                View.GONE
            arrow.tag = "down"
            binding.includeBodyDetailsPageNew.root.visibility = View.VISIBLE
            binding.includeBodyDetailsPage.root.visibility = View.VISIBLE
        }

    }
}