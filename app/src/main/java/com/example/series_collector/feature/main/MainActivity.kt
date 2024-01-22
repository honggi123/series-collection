package com.example.series_collector.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.series_collector.R
import com.example.series_collector.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )

        val navHomeFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        binding.bottomNavBar.setupWithNavController(navHomeFragment.navController)
    }
}