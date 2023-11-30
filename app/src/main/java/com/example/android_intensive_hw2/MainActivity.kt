package com.example.android_intensive_hw2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_intensive_hw2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}