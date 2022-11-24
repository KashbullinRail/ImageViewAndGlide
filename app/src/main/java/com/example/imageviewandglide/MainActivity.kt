package com.example.imageviewandglide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imageviewandglide.databinding.ActivityMainBinding
import java.net.URI
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetRI.setOnClickListener {
            getOnRandomImage()
        }
    }

    fun getOnRandomImage(): Boolean {
        val keyword = binding.etWordForSearch.text.toString()
        if (keyword.isBlank()) {
            binding.etWordForSearch.error = "Not input text"
            return true
        }
        val inputWord = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        Glide.with(this)
            .load("https://source.unsplash.com/random?$inputWord")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivImage)

        return false
    }
}