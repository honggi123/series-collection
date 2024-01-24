package com.example.series_collector.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.series_collector.R
import com.example.series_collector.databinding.ActivityMainBinding
import com.example.series_collector.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_splash)

        splashViewModel.isLoading.observe(this) { isFinish ->
            if (isFinish) startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.startActivity(this)
        finish()
    }
}