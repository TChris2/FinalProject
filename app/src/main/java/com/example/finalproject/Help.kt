package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Help : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val BHome = findViewById<Button>(R.id.BHome)

        //takes user back to the Level Select/Options.kt
        BHome.setOnClickListener {
            startActivity(Intent(this,Options::class.java))
        }
    }
}