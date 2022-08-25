package com.decagon.decafit.common.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.decafit.common.common.data.preferences.Preference.getName
import com.decagon.decafit.common.dashboard.dashBoardViewModel.DashBoardViewModel
import com.decagon.decafit.common.utils.onItemClickListener
import com.decagon.decafit.common.utils.snackBar
import com.decagon.decafit.databinding.FragmentDashBoardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardFragment : Fragment() {
    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerAdapter: DashBoardAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeMessage: TextView
    private lateinit var name: String
    private val viewModel: DashBoardViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashBoardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        welcomeMessage = binding.welcomeMsg
        name = getName("name").toString()
        networkObsever()
        getWorksObserver()
    }

    private fun getWorksObserver() {
        viewModel.getWorkOuts(requireContext())
        viewModel.getAllRepositoryList().observe(viewLifecycleOwner) { it ->
            recyclerAdapter = DashBoardAdapter(it)
            recyclerView.adapter = recyclerAdapter
            welcomeMessage.text = "Welcome $name"

            recyclerAdapter.setOnItemClickListener(object : onItemClickListener {
                override fun allAppsItemClicked(position: Int) {
                    findNavController().navigate(
                        DashBoardFragmentDirections.actionDashBoardFragmentToInputExerciseFragment(
                            it[position].title, it[position].backgroundImage
                        )
                    )
                }
            })


        }
    }


    private fun networkObsever(){
        viewModel.networkCheckResponse.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                snackBar(it)
            }
        }
    }
}

//        viewModel.dashBoardResponse.observe(viewLifecycleOwner){ resources->
//
//            when(resources) {
//                is UiState.Failure -> TODO()
//                is UiState.Success -> {
//                    recyclerAdapter = DashBoardAdapter(resources.data)
//                    recyclerView.adapter = recyclerAdapter
//                    welcomeMessage.text = "Welcome $name"
//
//                    recyclerAdapter.setOnItemClickListener(object : onItemClickListener {
//                        override fun allAppsItemClicked(position: Int) {
//                            findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToInputExerciseFragment(
//                                resources.data[position].title,  resources.data[position].backgroundImage
//                            ))
//                        }
//                    })

//                }
//            }
//        }
//   }
//        }

//            val workOuts = resources.data!!.workouts
//            if(resources != null && !resources.hasErrors()){
//                recyclerAdapter = DashBoardAdapter(workOuts)
//                recyclerView.adapter = recyclerAdapter
//                welcomeMessage.text = "Welcome $name"
//
//                recyclerAdapter.setOnItemClickListener(object : onItemClickListener {
//                    override fun allAppsItemClicked(position: Int) {
//                        findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToInputExerciseFragment(
//                            resources.data!!.workouts[position]!!.title,  resources.data!!.workouts[position]!!.backgroundImage
//                        ))
//                    }
//                })
//            }
//            if (resources.hasErrors()){
//                snackBar(resources.errors?.get(0)?.message!!)
//            }

//viewModel.dashBoardResponse.observe(viewLifecycleOwner){ resources->
//    val workOuts = resources.data!!.workouts
//    if(resources.data != null && !resources.hasErrors()){
//        recyclerAdapter = DashBoardAdapter(workOuts)
//        recyclerView.adapter = recyclerAdapter
//        welcomeMessage.text = "Welcome $name"
//
//        recyclerAdapter.setOnItemClickListener(object : onItemClickListener {
//            override fun allAppsItemClicked(position: Int) {
//                findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToInputExerciseFragment(
//                    resources.data!!.workouts[position]!!.title,  resources.data!!.workouts[position]!!.backgroundImage
//                ))
//            }
//        })
//    }
//    if (resources.hasErrors()){
//        snackBar(resources.errors?.get(0)?.message!!)
