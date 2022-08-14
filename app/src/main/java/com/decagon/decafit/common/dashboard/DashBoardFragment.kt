package com.decagon.decafit.common.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decagon.decafit.R
import com.decagon.decafit.databinding.FragmentDashBoardBinding

class DashBoardFragment : Fragment() {
    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashBoardBinding.inflate(inflater, container, false)

        binding.card1ExImage.setOnClickListener {
                findNavController().navigate(R.id.action_dashBoardFragment_to_inputExerciseFragment)
        }

        binding.exercise1.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_inputExerciseFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}