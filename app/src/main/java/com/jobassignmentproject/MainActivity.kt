package com.jobassignmentproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jobassignmentproject.NetworkLayer.RetrofitInstance
import com.jobassignmentproject.PresentationLayer.Ui.OpenWeather.OpenWeatherSecrren
import com.jobassignmentproject.PresentationLayer.utils.NetworkObserver
import com.jobassignmentproject.PresentationLayer.utils.SnackbarUtil
import com.jobassignmentproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initview()

        RetrofitInstance.setToken("")

        onClick()
    }

    private fun onClick() {

        binding.btnopenweather.setOnClickListener {

            startActivity(Intent(this, OpenWeatherSecrren::class.java))
            finish()

        }
    }


    @SuppressLint("SetTextI18n")
    private fun initview() {

        NetworkObserver.init(this)

        binding.txtmain.text = "Welcome to the app!"

        NetworkObserver.networkStatus.observe(this) { status ->
            if (status == "Slow Internet Connection" || status == "No Internet Connection") {
                SnackbarUtil.showShort(binding.root, status)
            }
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        NetworkObserver.unregister()
    }

}