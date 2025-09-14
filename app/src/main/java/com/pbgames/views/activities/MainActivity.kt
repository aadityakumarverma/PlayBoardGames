package com.pbgames.views.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.pbgames.R
import com.pbgames.databinding.ActivityMainBinding
import com.pbgames.utils.SharedPreferencesHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController : NavController? = null

    companion object{
        var appPackageName="" // please use the makePackageNameAvailableForFragment() fun to set packageName
        var mySystemBars: Insets = Insets.NONE
    }
    private fun makePackageNameAvailableForFragment() {
        appPackageName = packageName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            mySystemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            // Choose whichever is bigger (IME or navigation bar)
            val bottomInset = maxOf(mySystemBars.bottom, imeInsets.bottom)

            v.setPadding(mySystemBars.left, 0, mySystemBars.right, bottomInset)
            insets
        }


        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        sharedPreferencesHelper = SharedPreferencesHelper.Companion.getInstance(this)

        makePackageNameAvailableForFragment()

        val navHost = binding.navHostFragmentContainerView as? NavHostFragment
        navController = navHost?.findNavController()

        if (navController != null) {
            navController?.addOnDestinationChangedListener(destinationListener)
        }


        if (intent?.data != null && navController != null) {
            navController?.handleDeepLink(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        navController?.let {
            it.addOnDestinationChangedListener(destinationListener)
        }
    }

    override fun onPause() {
        navController?.let {
            it.removeOnDestinationChangedListener(destinationListener)
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        navController?.removeOnDestinationChangedListener(destinationListener)
    }


    @SuppressLint("RestrictedApi")
    private val destinationListener =  NavController.OnDestinationChangedListener { controller, destination, arguments ->

        Log.d("TAG_FRAGMENT_NAME",destination.displayName.toString())
        when(destination.id){
            R.id.SplashFragment->{
                Log.d("TAG_FRAGMENT_NAME","SplashFragment")
            }
            R.id.OnboardingScreenFragment->{
                Log.d("TAG_FRAGMENT_NAME","OnboardingScreenFragment")
            }
        }


    }



}