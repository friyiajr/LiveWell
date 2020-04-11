package com.oneonefivedigitalcreations.easyhrv.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.oneonefivedigitalcreations.easyhrv.R
import com.oneonefivedigitalcreations.easyhrv.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false)

        binding.startNow.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_homeFragment_to_startSurveyFragment)
        }
        return binding.root
    }
}
