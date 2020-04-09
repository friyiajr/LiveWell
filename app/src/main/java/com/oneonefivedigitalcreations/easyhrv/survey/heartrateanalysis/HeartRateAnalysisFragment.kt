package com.oneonefivedigitalcreations.easyhrv.survey.heartrateanalysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oneonefivedigitalcreations.easyhrv.R
import com.oneonefivedigitalcreations.easyhrv.databinding.FragmentStartSurveyBinding

/**
 * A simple [Fragment] subclass.
 */
class HeatRateAnalysisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStartSurveyBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_heart_rate_analysis, container, false)

        return binding.root
    }

}