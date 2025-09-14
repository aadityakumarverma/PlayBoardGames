package com.pbgames.views.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
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
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}