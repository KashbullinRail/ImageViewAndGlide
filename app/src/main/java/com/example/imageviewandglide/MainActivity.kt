package com.example.imageviewandglide

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imageviewandglide.databinding.ActivityMainBinding
import java.net.URI
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var enable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetRI.setOnClickListener {
            getOnRandomImage()
        }
        binding.etWordForSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener getOnRandomImage()
            }
            return@setOnEditorActionListener false
        }
        binding.checkBox.setOnClickListener {
            enableTV()
        }


    }

    fun getOnRandomImage(): Boolean {
        val keyword = binding.etWordForSearch.text.toString()
        if (binding.checkBox.isChecked && keyword.isBlank()) {
            binding.etWordForSearch.error = "Not input text"
            return true
        }
        val inputWord = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        binding.progressBar.visibility = View.VISIBLE
        Glide.with(this)
            .load(
                "https://source.unsplash.com/random" +
                        "${if (binding.checkBox.isChecked) "?$inputWord" else ""}"
            )
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?, target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                   binding.progressBar.visibility = View.GONE
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.ivImage)

        return false
    }

    fun enableTV() = with(binding) {
        if (checkBox.isChecked) {
            etWordForSearch.visibility = View.VISIBLE
        } else {
            etWordForSearch.visibility = View.GONE
        }
    }

}