package com.stellantis.crf.pms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stellantis.crf.pms.databinding.FragmentCoachingAdviseBinding
import com.stellantis.crf.pms.databinding.FragmentNotificationsBinding

class CoachingAdviseFragment : Fragment() {

    lateinit var binding: FragmentCoachingAdviseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCoachingAdviseBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }
}