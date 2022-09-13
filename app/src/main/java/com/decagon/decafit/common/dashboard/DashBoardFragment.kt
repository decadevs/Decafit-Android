package com.decagon.decafit.common.dashboard

import android.app.ActionBar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.common.data.preferences.Preference.getName
import com.decagon.decafit.common.dashboard.dashBoardViewModel.DashBoardViewModel
import com.decagon.decafit.common.utils.onItemClickListener
import com.decagon.decafit.common.utils.snackBar
import com.decagon.decafit.databinding.FragmentDashBoardBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


@AndroidEntryPoint
class DashBoardFragment : Fragment() {
    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerAdapter: DashBoardAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeMessage: TextView
    private lateinit var name: String
    private val viewModel: DashBoardViewModel by viewModels()
    private lateinit var date: LocalDate
    var dayOfMonth: Int = 0
    private var pressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar: ActionBar? = requireActivity().actionBar
        actionBar?.title = "Dashboard"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashBoardBinding.inflate(inflater, container, false)

        val activity = activity as AppCompatActivity?
        val actionBar: androidx.appcompat.app.ActionBar? = activity!!.supportActionBar
        actionBar?.title = "Dashboard"

        binding.profileContainer.setOnClickListener{ v ->
            (activity as DashBoardActivity).openCloseNavigationDrawer(v)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        welcomeMessage = binding.welcomeMsg
        name = getName("name").toString()

        date = LocalDate.now()

        dayOfMonth = date.dayOfMonth
        binding.date.text = dayOfMonth.toString()

        networkObsever()
        getWorksObserver()
        handleBackPress()
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
                    Preference.saveWorkoutId(it[position].id)
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

    override fun onResume() {
        super.onResume()
        val activity = activity as AppCompatActivity?
        val actionBar: androidx.appcompat.app.ActionBar? = activity!!.supportActionBar
        actionBar?.title = "Dashboard"
    }


    private fun handleBackPress(){
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val activity = activity as AppCompatActivity?
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    activity!!.finish()
                } else {
                    Toast.makeText(context, "Press back again to Logout", Toast.LENGTH_SHORT).show()
                }
                pressedTime = System.currentTimeMillis()
            }
        })
    }
}

