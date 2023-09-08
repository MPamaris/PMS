package com.stellantis.crf.pms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return view
    }

    private fun goToHomePage() {
        findNavController().navigate(R.id.action_detailsPageFragment_to_vehicleHealthPageFragment)
    }
}