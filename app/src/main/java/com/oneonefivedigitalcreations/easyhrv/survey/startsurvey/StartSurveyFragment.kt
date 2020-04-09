package com.oneonefivedigitalcreations.easyhrv.survey.startsurvey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

        return binding.root
    }

}
