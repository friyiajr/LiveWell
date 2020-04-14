package com.oneonefivedigitalcreations.easyhrv.survey.questionnaire

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oneonefivedigitalcreations.easyhrv.R
import com.oneonefivedigitalcreations.easyhrv.databinding.FragmentQuestionnaireBinding

class QuestionnaireFragment : Fragment() {

    private lateinit var binding: FragmentQuestionnaireBinding
    private lateinit var viewModel: QuestionnaireViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_questionnaire, container, false)

        initViewModel()
        subscribeToQuestionChanges()
        subscribeToQuestionTypeChanges()
        setCTAAction()

        return binding.root
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this).get(QuestionnaireViewModel::class.java)
    }

    private fun subscribeToQuestionChanges() {
        viewModel.currentQuestionString.observe(viewLifecycleOwner, Observer {
            binding.titleText.text = it
        })
    }

    private fun subscribeToQuestionTypeChanges() {
        viewModel.currentQuestionType.observe(viewLifecycleOwner, Observer {
            when (it) {
                QuestionType.SCALE -> {
                    binding.radioButtonGroup.visibility = View.GONE
                    binding.quantityLayout.visibility = View.GONE
                    binding.seekBarResponseLayout.visibility = View.VISIBLE
                }
                QuestionType.BOOLEAN -> {
                    binding.radioButtonGroup.visibility = View.VISIBLE
                    binding.quantityLayout.visibility = View.GONE
                    binding.seekBarResponseLayout.visibility = View.GONE
                }
                else -> {
                    binding.radioButtonGroup.visibility = View.GONE
                    binding.quantityLayout.visibility = View.VISIBLE
                    binding.seekBarResponseLayout.visibility = View.GONE
                }
            }
        })
    }

    private fun setCTAAction() {
        binding.nextButton.setOnClickListener {
            val progressBarValue: Int = binding.seekBar2.progress
            viewModel.startNextQuestion(progressBarValue)
        }
    }
}