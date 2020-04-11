package com.oneonefivedigitalcreations.easyhrv.survey.heartrateanalysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.oneonefivedigitalcreations.easyhrv.R
import com.oneonefivedigitalcreations.easyhrv.databinding.FragmentHeartRateAnalysisBinding

/**
 * A simple [Fragment] subclass.
 */
class HeatRateAnalysisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHeartRateAnalysisBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_heart_rate_analysis, container, false)

        binding.nextButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_heatRateAnalysisFragment_to_sleepAnalysisFragment))

        return binding.root
    }

}