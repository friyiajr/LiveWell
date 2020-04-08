package com.oneonefivedigitalcreations.easyhrv.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.oneonefivedigitalcreations.easyhrv.R
import com.oneonefivedigitalcreations.easyhrv.databinding.FragmentHeartRateAnalysisBinding

/**
 * A simple [Fragment] subclass.
 */
class HeartRateAnalysisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHeartRateAnalysisBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_heart_rate_analysis, container, false)

        return binding.root
    }

}
