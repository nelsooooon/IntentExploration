package com.example.intentexploration

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intentexploration.databinding.ActivityMessageTemplateBinding

class MessageTemplateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageTemplateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMessageTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val templates:ArrayList<String> =
            arrayListOf("Let’s meet up!", "Have you worked on the project?",
                "Movie time?", "Busy, do not disturb",
                "Why you leave me?!", "Please pay me a visit. Urgent!",
                "Please call me back")

        binding.recViewTemplate.layoutManager = LinearLayoutManager(this)
        binding.recViewTemplate.hasFixedSize()
        binding.recViewTemplate.adapter = TemplateAdapter(templates, this)
    }
}