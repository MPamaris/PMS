package com.stellantis.crf.pms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentCoachingAdviseBinding
import com.stellantis.crf.pms.model.CoachingAdviseInfo
import com.stellantis.crf.pms.model.NotificationInfo

class CoachingAdviseFragment : Fragment() {

    lateinit var binding: FragmentCoachingAdviseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCoachingAdviseBinding.inflate(layoutInflater)
        val view = binding.root

        binding.idIncludeToolbarCoaching.idButtonBackToolbarCoaching.setOnClickListener {
            goBack()
        }

        binding.idIncludeSomeCoachingAdvise.idCoachingAdviseShowMore.setOnClickListener {
            goToDetailsWithArguments()
        }

        return view
    }
    private fun goBack() {
        findNavController().navigate(R.id.action_coachingAdviseFragment_to_vehicleHealthPageFragment)
    }

    private fun goToDetailsWithArguments() {
        //Toast.makeText(activity, "!!", Toast.LENGTH_SHORT).show()
        val argumentsFromCoachingAdvise = "FROM_COACHING"
        val argsToSend = CoachingAdviseInfo(argumentsFromCoachingAdvise)

        val action = CoachingAdviseFragmentDirections.actionCoachingAdviseFragmentToDetailsPageFragment(argsToSend)
        findNavController().navigate(action)
    }
}