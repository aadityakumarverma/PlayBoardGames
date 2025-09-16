package com.pbgames.views.fragments.tic_tac

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pbgames.R
import com.pbgames.databinding.FragmentTicTacGameBinding
import com.pbgames.utils.SharedPreferencesHelper

class TicTacGameFragment : Fragment() {
    lateinit var binding: FragmentTicTacGameBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController

    lateinit var listBoxes : ArrayList<ImageView>

    var toggle = "cross"
    val crossContains = arrayListOf<String>()
    val circleContains = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTicTacGameBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController =findNavController()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            listBoxes = arrayListOf(ivBox00,ivBox01,ivBox02,ivBox11,ivBox12,ivBox13,ivBox21,ivBox22,ivBox23)

            setListeners(listBoxes)
        }
    }

    private fun setListeners(boxes: ArrayList<ImageView>) {

        boxes.forEach { box ->
            box.setOnClickListener {
                box.setImageResource(if (toggle == "cross") R.drawable.ic_cross else R.drawable.ic_circle)
                box.isEnabled = false
                toggle = if (toggle == "cross") "circle" else "cross"
            }

        }
    }
}