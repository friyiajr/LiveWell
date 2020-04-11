package com.oneonefivedigitalcreations.easyhrv.survey.sleepanalysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.oneonefivedigitalcreations.easyhrv.R
import com.oneonefivedigitalcreations.easyhrv.databinding.FragmentSleepAnalysisBinding

class SleepAnalysisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepAnalysisBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_sleep_analysis, container, false)

        return binding.root
    }

}