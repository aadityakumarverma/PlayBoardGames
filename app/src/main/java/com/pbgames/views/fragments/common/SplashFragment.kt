package com.pbgames.views.fragments.common

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pbgames.R
import com.pbgames.databinding.FragmentSplashBinding
import com.pbgames.utils.SharedPreferencesHelper
import com.pbgames.utils.UtilsFunctions.navOptionsPopUpIn
import com.pbgames.utils.UtilsFunctions.showLog

class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSplashBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController =findNavController()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val isOnboardingRead =sharedPreferencesHelper.getBoolean("KeyIsOnboardingRead",false)
        val isAlreadyLoggedIn =sharedPreferencesHelper.getBoolean("IsAlreadyLoggedIn",false)

        binding.vBg.isVisible=true


        Handler(Looper.getMainLooper()).postDelayed({
            if (isOnboardingRead) {
                if (isAlreadyLoggedIn) {
                    navController.navigate(R.id.TicTacGameFragment, null, navOptionsPopUpIn)
                } else {
                    navController.navigate(R.id.SelectGameFragment, null, navOptionsPopUpIn)
                }
            } else {
                navController.navigate(R.id.OnboardingScreenFragment, null, navOptionsPopUpIn)
            }
        },2600)


        return binding.root
    }
}