package com.example.android_intensive_hw2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.android_intensive_hw2.databinding.ActivityMainBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageURLEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                loadImage(binding.imageURLEditText.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadImage(url: String) {
        binding.progressBar.visibility = View.VISIBLE

        val urlImage = URL(url)
        val result: Deferred<Bitmap?> = GlobalScope.async {
            urlImage.toBitmap()
        }
        GlobalScope.launch(Dispatchers.Main) {
            binding.imageView.setImageBitmap(result.await())
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun URL.toBitmap(): Bitmap?{
        return try {
            BitmapFactory.decodeStream(openStream())
        }catch (e:IOException){
            null
        }
    }
}