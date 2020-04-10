package com.oneonefivedigitalcreations.easyhrv.survey.startsurvey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.oneonefivedigitalcreations.easyhrv.R
import com.oneonefivedigitalcreations.easyhrv.databinding.FragmentStartSurveyBinding


/**
 * A simple [Fragment] subclass.
 */
class StartSurveyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStartSurveyBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_start_survey, container, false)

        binding.startNow.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_startSurveyFragment_to_heatRateAnalysisFragment))

        return binding.root
    }

}
