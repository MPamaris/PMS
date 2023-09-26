package com.stellantis.crf.pms.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stellantis.crf.pms.R
import com.stellantis.crf.pms.databinding.FragmentUserFeedbackBinding

class UserFeedbackFragment : Fragment() {

    lateinit var binding: FragmentUserFeedbackBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserFeedbackBinding.inflate(layoutInflater)
        val view = binding.root

        binding.idIncludeBodyUserFeedback.idDescUserFeedbackChooseTwo.setOnClickListener {
            binding.idIncludeBodyUserFeedbackChoice.root.visibility = View.VISIBLE
        }

        binding.idIncludeBodyUserFeedbackChoice.idButtonConfirmChoice.setOnClickListener {
            if (binding.idIncludeBodyUserFeedbackChoice.idDescUserFeedbackChooseThreePost.isChecked) {
                binding.idIncludeToolbarUserFeedback.root.visibility = View.GONE
                binding.idIncludeBodyUserFeedback.root.visibility = View.GONE
                binding.idIncludeBodyUserFeedbackChoice.root.visibility = View.GONE

                binding.idIncludeBodyUserFeedbackPopupLoading.root.visibility = View.VISIBLE
                binding.idMainLayoutFeedback.setBackgroundColor(R.color.black)
            }
        }

        binding.idIncludeBodyUserFeedbackPopupLoading.idButtonClosePopupLoading.setOnClickListener {
            findNavController().navigate(R.id.action_userFeedbackFragment_to_detailsPageFragment)
        }

        binding.idIncludeToolbarUserFeedback.textView.setOnClickListener {
            goBack()
        }

        return view
    }

    private fun goBack() {
        findNavController().navigate(R.id.action_userFeedbackFragment_to_homeFragment)
    }
}