package com.pbgames.views.fragments.ludo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pbgames.R
import com.pbgames.databinding.FragmentLudoBinding
import com.pbgames.databinding.FragmentTicTacGameBinding
import com.pbgames.utils.SharedPreferencesHelper
import com.pbgames.views.activities.MainActivity.Companion.mySystemBars

class LudoFragment : Fragment() {
    private var _binding: FragmentLudoBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController

    lateinit var listBoxes : ArrayList<ImageView>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLudoBinding.inflate(inflater, container, false)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController =findNavController()

        binding.llAppBar.setPadding(0, mySystemBars.top,0,0)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
//            listBoxes = arrayListOf(ivBox00,ivBox01,ivBox02, ivBox10,ivBox11,ivBox12, ivBox20,ivBox21,ivBox22)
//            listBoxes.forEach { it.tag = "none" }


//            setListeners(listBoxes)
        }
    }

}