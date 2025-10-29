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
import com.pbgames.utils.AnimationUtils.springScale
import com.pbgames.utils.SharedPreferencesHelper
import com.pbgames.views.activities.MainActivity.Companion.mySystemBars

class TicTacGameFragment : Fragment() {
    private var _binding: FragmentTicTacGameBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController

    lateinit var listBoxes : ArrayList<ImageView>

    var toggle = "cross"

    var alternateStarter = true

    var player1Score = 0
    var player2Score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTicTacGameBinding.inflate(inflater, container, false)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController =findNavController()

        binding.llAppBar.setPadding(0, mySystemBars.top,0,0)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            listBoxes = arrayListOf(ivBox00,ivBox01,ivBox02, ivBox10,ivBox11,ivBox12, ivBox20,ivBox21,ivBox22)
            listBoxes.forEach { it.tag = "none" }


            setListeners(listBoxes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners(boxes: ArrayList<ImageView>) {

        boxes.forEach { box ->
            box.setOnClickListener {
                it.springScale()
                box.setImageResource(if (toggle == "cross") R.drawable.icon_cross else R.drawable.icon_circle)
                box.isEnabled = false
                box.tag = toggle
                checkWinner()
                toggle = if (toggle == "cross") "circle" else "cross"
            }

        }
    }

    private fun checkWinner() {
        binding.apply {
            val winLines = listOf(
                listOf(ivBox00, ivBox01, ivBox02),
                listOf(ivBox10, ivBox11, ivBox12),
                listOf(ivBox20, ivBox21, ivBox22),
                listOf(ivBox00, ivBox10, ivBox20),
                listOf(ivBox01, ivBox11, ivBox21),
                listOf(ivBox02, ivBox12, ivBox22),
                listOf(ivBox00, ivBox11, ivBox22),
                listOf(ivBox20, ivBox11, ivBox02)
            )

            for (line in winLines){
                val tag1 = line[0].tag.toString()
                val tag2 = line[1].tag.toString()
                val tag3 = line[2].tag.toString()

                if (tag1 == tag2 && tag2 == tag3 && tag1 != "none"){
                    if (tag1 == "cross") increasePlayer1Score() else increasePlayer2Score()
                    return
                }

            }

            if (listBoxes.all { it.tag != "none" }) {
                resetBoard()
            }

        }
    }


    private fun increasePlayer1Score() {
        player1Score++
        binding.tvPlayer1Score.text = String.format("%02d", player1Score)
        resetBoard()
    }

    private fun increasePlayer2Score() {
        player2Score++
        binding.tvPlayer2Score.text = String.format("%02d", player2Score)
        resetBoard()
    }
    private fun resetBoard() {
        listBoxes.forEach { box ->
            box.setImageResource(0)
            box.isEnabled = true
            box.tag = "none"
        }
        if (alternateStarter) {
            toggle = "circle"
            alternateStarter = false
        } else {
            toggle = "cross" // alternate starter
            alternateStarter = true
        }
    }

    private fun restartGame() {
        player1Score = 0
        player2Score = 0
        binding.tvPlayer1Score.text = String.format("%02d", player1Score)
        binding.tvPlayer2Score.text = String.format("%02d", player2Score)
        resetBoard()
    }
}