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

    var player1Score = 0
    var player2Score = 0

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
            listBoxes = arrayListOf(ivBox00,ivBox01,ivBox02, ivBox10,ivBox11,ivBox12, ivBox20,ivBox21,ivBox22)

            setListeners(listBoxes)
        }
    }

    private fun setListeners(boxes: ArrayList<ImageView>) {

        boxes.forEach { box ->
            box.setOnClickListener {
                box.setImageResource(if (toggle == "cross") R.drawable.ic_cross else R.drawable.ic_circle)
                box.isEnabled = false
                box.tag = toggle
                checkWinner()
                toggle = if (toggle == "cross") "circle" else "cross"
            }

        }
    }

    private fun checkWinner() {
        binding.apply {
            val tag00 = ivBox00.tag
            val tag01 = ivBox01.tag
            val tag02 = ivBox02.tag
            val tag10 = ivBox10.tag
            val tag11 = ivBox11.tag
            val tag12 = ivBox12.tag
            val tag20 = ivBox20.tag
            val tag21 = ivBox21.tag
            val tag22 = ivBox22.tag

            if ((tag00 == tag01 && tag01 == tag02 && tag02 != "none") ||
                (tag10 == tag11 && tag11 == tag12 && tag12 != "none") ||
                (tag20 == tag21 && tag21 == tag22 && tag22 != "none") ||

                (tag00 == tag10 && tag10 == tag20 && tag20 != "none") ||
                (tag01 == tag11 && tag11 == tag21 && tag21 != "none") ||
                (tag02 == tag12 && tag12 == tag22 && tag22 != "none") ||

                (tag00 == tag11 && tag11 == tag22 && tag22 != "none") ||
                (tag20 == tag11 && tag11 == tag02 && tag02 != "none") ){

                if (tag00 == "cross") {
                    increasePlayer1Score()
                } else {
                    increasePlayer2Score()
                }
            }

        }
    }

    private fun increasePlayer1Score() {
        player1Score++
        binding.tvPlayer1Score.text = String.format("%2d", player1Score)
        resetBoard()
    }

    private fun increasePlayer2Score() {
        player2Score++
        binding.tvPlayer2Score.text = String.format("%2d", player2Score)
        resetBoard()
    }
    private fun resetBoard() {
        listBoxes.forEach { box ->
            box.setImageResource(0)
            box.isEnabled = true
            box.tag = "none"
        }
        toggle = "cross"
    }

    private fun restartGame() {
        player1Score = 0
        player2Score = 0
        binding.tvPlayer1Score.text = String.format("%2d", player1Score)
        binding.tvPlayer2Score.text = String.format("%2d", player2Score)
        resetBoard()
    }
}